package priv.fjh.mydubbo.transport;

/**
 * @author fjh
 * @date 2020/12/31 9:31
 * @Description:
 */
public interface RpcServer {

    <T> void publishService(T service, String serviceName);

    void start();
}
