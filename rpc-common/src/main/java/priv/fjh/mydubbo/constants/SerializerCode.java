package priv.fjh.mydubbo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author fjh
 * @date 2021/1/2 15:46
 * @Description: 字节流中标识序列化和反序列化器
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    KRYO(0),
    JSON(1);

    private final int code;

}
