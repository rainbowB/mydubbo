package priv.fjh.mydubbo.register;

/**
 * @author fjh
 * @date 2020/12/28 10:07
 * @Description:
 */
public interface ServiceRegister {

    //注册服务
    <T> void register(T service);

    //根据实现接口服务名获取服务的对象
    Object getService(String serviceName);
}
