package priv.fjh.mydubbo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fjh
 * @date 2021/1/19 19:55
 * @Description: Service 注解的值定义为该服务的名称，默认值是该类的完整类名
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    String name() default "";
}
