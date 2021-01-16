package priv.fjh.mydubbo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import priv.fjh.mydubbo.constants.RpcResponseCode;

import java.io.Serializable;

/**
 * @author fjh
 * @date 2020/12/10 10:20
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = 4095505365736160846L;
    /**
     * 响应对应的请求号
     */
    private String requestId;

    private Integer code;

    private String msg;

    private T data;

    public static <T> RpcResponse<T> getSuccess(String requestId, T data){
        return new RpcResponse<>(requestId, RpcResponseCode.SUCCESS.getCode(), RpcResponseCode.SUCCESS.getMsg(), data);
    }

    public static <T> RpcResponse<T> getFailure(RpcResponseCode rpcResponseCode){
        return new RpcResponse<>(null, rpcResponseCode.getCode(), rpcResponseCode.getMsg(), null);
    }

}
