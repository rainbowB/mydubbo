package priv.fjh.mydubbo.transport;

import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.annotation.Service;
import priv.fjh.mydubbo.annotation.ServiceScan;
import priv.fjh.mydubbo.constants.RpcErrorEnum;
import priv.fjh.mydubbo.exception.RpcException;
import priv.fjh.mydubbo.provider.ServiceProvider;
import priv.fjh.mydubbo.registry.ServiceRegistry;
import priv.fjh.mydubbo.utils.reflect.ReflectUtil;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author fjh
 * @date 2021/1/19 20:34
 * @Description:
 */
@Slf4j
public abstract class AbstractRpcServer implements RpcServer{
    protected String host;
    protected int port;
    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanServices() {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            //判断启动类上有没有ServiceScan注释
            if(!startClass.isAnnotationPresent(ServiceScan.class)) {
                log.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcErrorEnum.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            log.error("出现未知错误");
            throw new RpcException(RpcErrorEnum.UNKNOWN_ERROR);
        }
        //如果启动类上有ServiceScan注释，则获取改注释的值
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if("".equals(basePackage)) {
            //获取扫描范围
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for(Class<?> clazz : classSet) {
            //找到含有Service注解的类，使用反射创建对象，并调用publishService方法完成服务的注册
            if(clazz.isAnnotationPresent(Service.class)) {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("创建 " + clazz + " 的对象时有错误发生");
                    continue;
                }
                if("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface: interfaces){
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.registerService(serviceName, new InetSocketAddress(host, port));
    }
}
