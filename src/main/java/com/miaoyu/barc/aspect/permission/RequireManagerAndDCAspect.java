package com.miaoyu.barc.aspect.permission;

import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.enumeration.UserIdentityEnum;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequireManagerAndDCAspect {
    @Autowired
    private UserArchiveMapper userArchiveMapper;

    @Around("@annotation(RequireManagerAndDCAnno)")
    public Object requireManagerAndDCAspect(ProceedingJoinPoint pj) throws Throwable {
//        String uuid = pj.getArgs()[0].toString();
//        System.out.println("uuid" + uuid);
//        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
//        if (userArchive.getIdentity() == UserIdentityEnum.MANAGER) {
//            if (userArchive.getPermission() == PermissionConst.DISCIPLINARY_COMMITTEE) {
//                return pj.proceed();
//            }
//        }
        return ResponseEntity.ok(new UserR().insufficientAccountPermission());
    }
}
