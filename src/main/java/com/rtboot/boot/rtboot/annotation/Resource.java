package com.rtboot.boot.rtboot.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 从beanFactory查询需要的object并注入
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {
    /**
     * bean的名字，如果为空，则使用bean的自定名称或者全类名
     */
    String value() default "";
}
