package priv.fjh.mydubbo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author fjh
 * @date 2020/11/24 11:05
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hello implements Serializable {
    private Integer id;
    private String message;
}
