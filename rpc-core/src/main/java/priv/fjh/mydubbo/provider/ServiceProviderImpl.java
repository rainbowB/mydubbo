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
 * @Description: 实现了 ServiceProvider 接口，可以将其看做是一个保存和提供服务实例对象的示例
 */
@Slf4j
public class ServiceProviderImpl implements ServiceProvider {

    //例如对象A实现了接口X和Y，则map的key为X，value为A；key为Y，value为A。
    private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    //set存放了注册服务的对象名称，即A。
    private static Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void addServiceProvider(T service, String serviceName) {
        //父类引用指向子类对象时，canonicalName是子类名称。即下面注释获得的是HelloServiceImpl
        //String canonicalName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceName)){
            return;
        }
        registeredService.add(serviceName);
        serviceMap.put(serviceName, service);
        log.info("注册服务: {}, 添加接口: {}", serviceName, service.getClass().getInterfaces());
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
