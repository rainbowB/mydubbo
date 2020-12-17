package priv.fjh.mydubbo;

import priv.fjh.mydubbo.impl.HelloServiceImpl;

/**
 * @author fjh
 * @date 2020/12/10 14:01
 * @Description:
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}
