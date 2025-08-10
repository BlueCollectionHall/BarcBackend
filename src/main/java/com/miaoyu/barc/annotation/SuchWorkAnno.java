package com.miaoyu.barc.annotation;

public @interface SuchWorkAnno {
    String selectType();
    int index() default 1;
}
