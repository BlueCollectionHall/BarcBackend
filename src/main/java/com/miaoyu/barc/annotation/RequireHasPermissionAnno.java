package com.miaoyu.barc.annotation;

import com.miaoyu.barc.user.enumeration.UserIdentityEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireHasPermissionAnno {
    int uuidIndex() default 0;
    UserIdentityEnum identity() default UserIdentityEnum.USER;
    int permission() default 1;
}
