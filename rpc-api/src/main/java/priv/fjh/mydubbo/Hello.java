package priv.fjh.mydubbo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fjh
 * @date 2020/11/24 11:05
 * @Description:
 */
@Data
@AllArgsConstructor
public class Hello implements Serializable {
    private Integer id;
    private String message;
}
