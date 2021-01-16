package priv.fjh.mydubbo.provider;

/**
 * @author fjh
 * @date 2020/12/28 10:07
 * @Description:
 */
public interface ServiceProvider {

    //保存服务提供者
    <T> void addServiceProvider(T service);

    //获取服务提供者
    Object getServiceProvider(String serviceName);
}
