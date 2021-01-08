package priv.fjh.mydubbo;

import priv.fjh.mydubbo.impl.HelloServiceImpl;
import priv.fjh.mydubbo.netty.server.NettyServer;
import priv.fjh.mydubbo.register.DefaultServiceRegister;
import priv.fjh.mydubbo.register.ServiceRegister;


/**
 * @author fjh
 * @date 2021/1/3 15:36
 * @Description:
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegister register = new DefaultServiceRegister();
        register.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
