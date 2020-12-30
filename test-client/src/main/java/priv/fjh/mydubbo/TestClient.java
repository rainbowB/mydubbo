package priv.fjh.mydubbo;

import priv.fjh.mydubbo.client.RpcClientProxy;

/**
 * @author fjh
 * @date 2020/12/10 14:03
 * @Description:
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = (HelloService) proxy.getProxy(HelloService.class);
        Hello object = new Hello(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
