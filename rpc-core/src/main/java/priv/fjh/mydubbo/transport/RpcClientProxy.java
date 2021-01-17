package priv.fjh.mydubbo.transport;

import lombok.AllArgsConstructor;
import priv.fjh.mydubbo.dto.RpcRequest;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

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

    /**
     * 当你使用代理对象调用方法的时候实际会调用到这个方法。代理对象就是你通过上面的 getProxy 方法获取到的对象。
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //客户端生成并调用代理对象时，会将对应的service封装到RpcRequest中，然后传给服务端。服务端解析出来后调用相应的方法
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .build();
        return rpcClient.sendRequest(rpcRequest);
    }
}
