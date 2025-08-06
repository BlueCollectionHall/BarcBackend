package com.miaoyu.barc;

import com.miaoyu.barc.annotation.permission.RequireManagerAndDCAnno;
import com.miaoyu.barc.response.SuccessR;
import com.miaoyu.barc.utils.J;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    @RequireManagerAndDCAnno
    public ResponseEntity<J> testPermissionAnno(String uuid) {
        return ResponseEntity.ok(new SuccessR().normal("权限正常"));
    }
}
