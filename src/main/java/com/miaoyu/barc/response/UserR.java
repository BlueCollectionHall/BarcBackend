package com.miaoyu.barc.response;

import com.miaoyu.barc.utils.J;

public class UserR {
    public J noSuchUser() {
        J j = new J();
        j.setCode(1);
        j.setMsg("未找到用户信息：No such user basic information");
        j.setData(j.getMsg());
        return j;
    }
}
