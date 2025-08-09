package com.miaoyu.barc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireSelfOrManagerAnno {
    int uuidIndex() default 0;
    int authorUuidIndex() default 1;

    int managerPermission() default 0;

    boolean isHasElseUpper() default false;
}
