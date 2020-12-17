package priv.fjh.mydubbo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author fjh
 * @date 2020/12/10 10:27
 * @Description:
 */
@Getter
@AllArgsConstructor
@ToString
public enum RpcResponseCode {

    SUCCESS(200,"调用方法成功"),
    FAIL(500,"调用方法失败"),
    NOT_FOUND_METHOD(500,"未找到指定方法"),
    NOT_FOUND_CLASS(500,"未找到指定类");

    private final Integer code;
    private final String msg;

}
