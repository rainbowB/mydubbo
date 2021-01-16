package priv.fjh.mydubbo;

import priv.fjh.mydubbo.impl.HelloServiceImpl;

/**
 * @author fjh
 * @date 2020/12/10 14:01
 * @Description:
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        //之前是直接将service作为参数传递，现在改成将service注册，然后根据服务名自己去获取服务对象
        /*SocketServer socketServer = new SocketServer("127.0.0.1", 9000);
        socketServer*/
    }
}
