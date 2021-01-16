package priv.fjh.mydubbo.handler;

import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.constants.RpcResponseCode;
import priv.fjh.mydubbo.dto.RpcRequest;
import priv.fjh.mydubbo.dto.RpcResponse;
import priv.fjh.mydubbo.provider.ServiceProvider;
import priv.fjh.mydubbo.provider.ServiceProviderImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author fjh
 * @date 2020/12/30 10:27
 * @Description:
 */
@Slf4j
public class RequestHandler {
    private static final ServiceProvider serviceProvider;

    static {
        serviceProvider = new ServiceProviderImpl();
    }

    /**
     * 原先是handle(RpcRequest rpcRequest, Object service)，现在通过使用服务名去注册中心获取
     */
    public Object handle(RpcRequest rpcRequest) {
        Object result = null;
        Object service = RequestHandler.serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        try {
            result = invokeTargetMethod(rpcRequest, service);
            log.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("调用或发送时有错误发生：", e);
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws IllegalAccessException, InvocationTargetException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.getFailure(RpcResponseCode.NOT_FOUND_METHOD);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
