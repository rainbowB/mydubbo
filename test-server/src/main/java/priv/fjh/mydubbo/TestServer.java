package priv.fjh.mydubbo;

import priv.fjh.mydubbo.impl.HelloServiceImpl;
import priv.fjh.mydubbo.register.DefaultServiceRegister;
import priv.fjh.mydubbo.register.ServiceRegister;
import priv.fjh.mydubbo.server.RpcServer;

/**
 * @author fjh
 * @date 2020/12/10 14:01
 * @Description:
 */
public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        //之前是直接将service作为参数传递，现在改成将service注册，然后根据服务名自己去获取服务对象
        ServiceRegister serviceRegister = new DefaultServiceRegister();
        serviceRegister.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegister);
        rpcServer.start(9000);
    }
}
