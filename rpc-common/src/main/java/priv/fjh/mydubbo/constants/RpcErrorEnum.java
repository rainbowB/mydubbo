package priv.fjh.mydubbo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author fjh
 * @date 2020/12/29 9:02
 * @Description:
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcErrorEnum {

    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    SERVICE_NOT_FOUND("没有找到指定的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务没有实现任何接口");

    private final String message;

}
