package com.lsatin.topclass.web.basic.dao.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Column {
    String value() default "";
    String table() default "";
    boolean nullable() default true;
}
