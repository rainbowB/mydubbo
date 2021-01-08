package priv.fjh.mydubbo;

import priv.fjh.mydubbo.socket.SocketClient;

/**
 * @author fjh
 * @date 2020/12/10 14:03
 * @Description:
 */
public class SocketTestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = (HelloService) proxy.getProxy(HelloService.class);
        Hello object = new Hello(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
