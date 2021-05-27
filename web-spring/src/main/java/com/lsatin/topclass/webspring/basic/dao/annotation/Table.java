package com.lsatin.topclass.webspring.basic.dao.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {
    String value() default "";
}
