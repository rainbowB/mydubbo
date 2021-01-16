package priv.fjh.mydubbo.utils.checker;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import priv.fjh.mydubbo.constants.RpcErrorEnum;
import priv.fjh.mydubbo.constants.RpcResponseCode;
import priv.fjh.mydubbo.dto.RpcRequest;
import priv.fjh.mydubbo.dto.RpcResponse;
import priv.fjh.mydubbo.exception.RpcException;

/**
 * @author fjh
 * @date 2021/1/13 21:39
 * @Description:
 */
@Slf4j
@NoArgsConstructor
public class RpcMessageChecker {
    private static final String INTERFACE_NAME = "interfaceName";

    public static void check(RpcResponse rpcResponse, RpcRequest rpcRequest) {
        if (rpcResponse == null) {
            log.error("调用服务失败,serviceName:{}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcErrorEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            log.error("调用服务失败,rpcRequest 和 rpcResponse 对应不上,serviceName:{},RpcResponse:{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcErrorEnum.REQUEST_NOT_MATCH_RESPONSE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getCode() == null || !rpcResponse.getCode().equals(RpcResponseCode.SUCCESS.getCode())) {
            log.error("调用服务失败,serviceName:{},RpcResponse:{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcErrorEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }
}
