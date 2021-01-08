package priv.fjh.mydubbo;

import priv.fjh.mydubbo.netty.client.NettyClient;

/**
 * @author fjh
 * @date 2021/1/3 15:40
 * @Description:
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = (HelloService) rpcClientProxy.getProxy(HelloService.class);
        Hello object = new Hello(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}