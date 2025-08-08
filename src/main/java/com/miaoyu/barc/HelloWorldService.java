package com.miaoyu.barc;

import com.miaoyu.barc.annotation.RequireSuchAndPermissionAnno;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.user.enumeration.UserIdentityEnum;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    @RequireSuchAndPermissionAnno(@RequireSuchAndPermissionAnno.Check(
            isSuchElseRequire = false, identity = UserIdentityEnum.MANAGER, targetPermission = PermissionConst.SEC_MAINTAINER
    ))
    public ResponseEntity<J> testPermissionAnno(String uuid) {
        return ResponseEntity.ok(new SuccessR().normal("注解放行正常"));
    }
}
