package priv.fjh.mydubbo;

import priv.fjh.mydubbo.transport.RpcClient;
import priv.fjh.mydubbo.transport.RpcClientProxy;
import priv.fjh.mydubbo.transport.netty.client.NettyClient;

/**
 * @author fjh
 * @date 2021/1/3 15:40
 * @Description:
 */
public class NettyTestClient {
    public static void main(String[] args) throws InterruptedException {
        RpcClient client = new NettyClient();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);

        HelloService helloService = (HelloService) rpcClientProxy.getProxy(HelloService.class);
        String res = helloService.hello(new Hello(1, "This is a message"));
        System.out.println(res);

        ByeService byeService = (ByeService) rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
        /*Thread.sleep(12000);
        for (int i = 0; i < 50; i++) {
            String des = helloService.hello(new Hello(2, "~~~" + i));
            System.out.println(des);
        }*/
    }
}
