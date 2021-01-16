package priv.fjh.mydubbo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author fjh
 * @date 2020/12/7 16:09
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 2999692917810775453L;
    /**
     * 请求号
     */
    private String requestId;
    /**
     * 待调用接口名称
     */
    private String interfaceName;
    /**
     * 待调用方法名称
     */
    private String methodName;
    /**
     * 调用方法的参数
     */
    private Object[] parameters;
    /**
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;

}
