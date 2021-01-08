package priv.fjh.mydubbo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author fjh
 * @date 2021/1/2 16:31
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

}
