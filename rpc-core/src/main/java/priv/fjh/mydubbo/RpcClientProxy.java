package priv.fjh.mydubbo;

import lombok.AllArgsConstructor;
import priv.fjh.mydubbo.RpcClient;
import priv.fjh.mydubbo.dto.RpcRequest;
import priv.fjh.mydubbo.dto.RpcResponse;
import priv.fjh.mydubbo.socket.SocketClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author fjh
 * @date 2020/12/7 16:59
 * @Description:
 */
@AllArgsConstructor
public class RpcClientProxy implements InvocationHandler {

    private final RpcClient rpcClient;

    public Object getProxy(Class clazz){
        //此处第二个参数不能使用clazz.getInterfaces()，因为传进来的clazz参数是HelloService，是一个接口。
        //只有clazz是HelloSericeImpl，即接口的实现类时，才能执行clazz.getInterfaces()来获取被代理对象实现的所有的接口
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //客户端生成并调用代理对象时，会将对应的service封装到RpcRequest中，然后传给服务端。服务端解析出来后调用相应的方法
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .build();
        //不在这个方法中调用method.invoke()，因为客户端没有相应的实现方法，只有接口
        return rpcClient.sendRequest(rpcRequest);
    }
}
