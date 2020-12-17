package priv.fjh.mydubbo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import priv.fjh.mydubbo.constants.RpcResponseCode;

import java.io.Serializable;

/**
 * @author fjh
 * @date 2020/12/10 10:20
 * @Description:
 */
@Data
@AllArgsConstructor
public class RpcResponse<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public static <T> RpcResponse<T> getSuccess(T data){
        return new RpcResponse<>(RpcResponseCode.SUCCESS.getCode(), null, data);
    }

    public static <T> RpcResponse<T> getFailure(T data){
        return new RpcResponse<>(RpcResponseCode.FAIL.getCode(), null, data);
    }

}
