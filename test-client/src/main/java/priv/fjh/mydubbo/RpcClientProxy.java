package priv.fjh.mydubbo;

import priv.fjh.mydubbo.dto.RpcRequest;
import priv.fjh.mydubbo.dto.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author fjh
 * @date 2020/12/7 16:59
 * @Description:
 */
public class RpcClientProxy implements InvocationHandler {
    private String host;
    private int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object getProxy(Class clazz){
        //此处第二个参数不能使用clazz.getInterfaces()，因为传进来的clazz参数是HelloService，是一个接口。
        //只有clazz是HelloSericeImpl，即接口的实现类时，才能执行clazz.getInterfaces()来获取接口的方法
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .build();
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse)rpcClient.sendRequest(rpcRequest,host,port)).getData();
    }
}
