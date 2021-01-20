package priv.fjh.mydubbo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fjh
 * @date 2021/1/19 20:07
 * @Description: ServiceScan 的值定义为扫描范围的根包，默认值为入口类所在的包，
 * 扫描时会扫描该包及其子包下所有的类，找到标记有 Service 的类，并注册。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScan {
    String value() default "";
}
