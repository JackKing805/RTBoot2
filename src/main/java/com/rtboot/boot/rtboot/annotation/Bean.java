package com.rtboot.boot.rtboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用bean注解后，ioc容器会自动扫描并创建该类的实列
 *
 * value 自定义bean的名字
 * 默认名称是class.getName()
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    public String value() default "";
}
