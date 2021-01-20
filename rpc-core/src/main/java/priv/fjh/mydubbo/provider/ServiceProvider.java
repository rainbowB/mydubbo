package priv.fjh.mydubbo.provider;

/**
 * @author fjh
 * @date 2020/12/28 10:07
 * @Description:
 */
public interface ServiceProvider {

    /**
     * 保存服务实例对象和服务实例对象实现的接口类的对应关系
     *
     * @param service      服务实例对象
     * @param serviceName 服务实例对象实现的接口类名
     */
    <T> void addServiceProvider(T service, String serviceName);

    /**
     * 获取服务实例对象
     *
     * @param serviceName 服务实例对象实现的接口类的类名
     * @return 服务实例对象
     */
    Object getServiceProvider(String serviceName);
}
