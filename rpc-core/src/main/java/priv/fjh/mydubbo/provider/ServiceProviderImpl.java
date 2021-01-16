package priv.fjh.mydubbo.provider;

import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.constants.RpcErrorEnum;
import priv.fjh.mydubbo.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fjh
 * @date 2020/12/28 10:09
 * @Description:
 */
@Slf4j
public class ServiceProviderImpl implements ServiceProvider {

    //例如对象A实现了接口X和Y，则map的key为X，value为A；key为Y，value为A。
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    //set存放了注册服务的对象名称，即A。
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void addServiceProvider(T service) {
        //父类引用指向子类对象时，canonicalName是子类名称
        String canonicalName = service.getClass().getCanonicalName();
        if(registeredService.contains(canonicalName)){
            return;
        }
        registeredService.add(canonicalName);
        //interfaces是service所实现的接口类
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0) {
            throw new RpcException(RpcErrorEnum.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for(Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        log.info("添加接口: {}, 注册服务: {}", interfaces, canonicalName);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null) {
            throw new RpcException(RpcErrorEnum.SERVICE_NOT_FOUND);
        }
        return service;
    }
}