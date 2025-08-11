package com.miaoyu.barc.annotation;

import com.miaoyu.barc.comment.model.WorkCommentModel;

public @interface SuchWorkCommentAnno {
    String commentAndReply();
    String selectType();
    int index() default 0;
}
