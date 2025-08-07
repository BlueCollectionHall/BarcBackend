package com.miaoyu.barc;

import com.miaoyu.barc.annotation.permission.RequireHasPermissionAnno;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.user.enumeration.UserIdentityEnum;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    @RequireHasPermissionAnno(
            identity = UserIdentityEnum.MANAGER,
            permission = PermissionConst.ADVANCED_ADMINISTRATOR
    )
    public ResponseEntity<J> testPermissionAnno(String uuid) {
        return ResponseEntity.ok(new SuccessR().normal("权限正常"));
    }
}
