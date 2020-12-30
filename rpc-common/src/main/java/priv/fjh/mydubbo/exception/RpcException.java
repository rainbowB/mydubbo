package priv.fjh.mydubbo.exception;

import priv.fjh.mydubbo.constants.RpcErrorEnum;

/**
 * @author fjh
 * @date 2020/12/29 9:03
 * @Description:
 */
public class RpcException extends RuntimeException{
    public RpcException(RpcErrorEnum rpcErrorEnum, String detail) {
        super(rpcErrorEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorEnum rpcErrorEnum) {
        super(rpcErrorEnum.getMessage());
    }
}
