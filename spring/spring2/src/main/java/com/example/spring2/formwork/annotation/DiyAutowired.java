package com.example.spring2.formwork.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * lingfan
 * 2019-07-25 12:00
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface DiyAutowired {
    String value() default "";
}
