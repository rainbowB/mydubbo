package priv.fjh.mydubbo;

import priv.fjh.mydubbo.dto.RpcRequest;

/**
 * @author fjh
 * @date 2020/12/31 9:31
 * @Description:
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
