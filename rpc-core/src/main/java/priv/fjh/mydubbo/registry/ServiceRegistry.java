package priv.fjh.mydubbo.registry;

import java.net.InetSocketAddress;

/**
 * @author fjh
 * @date 2021/1/10 10:32
 * @Description: 服务注册中心接口
 */
public interface ServiceRegistry {
    /**
     * 注册服务
     * @param serviceName       服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void registerService(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 查找服务
     * @param serviceName 服务名称
     * @return 提供服务的地址
     */
    InetSocketAddress lookupService(String serviceName);
}
