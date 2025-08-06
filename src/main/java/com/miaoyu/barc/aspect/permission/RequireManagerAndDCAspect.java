package com.miaoyu.barc.aspect.permission;

import com.miaoyu.barc.annotation.permission.RequireManagerAndDCAnno;
import com.miaoyu.barc.permission.ComparePermission;
import com.miaoyu.barc.permission.PermissionConst;
import com.miaoyu.barc.response.ErrorR;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.enumeration.UserIdentityEnum;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequireManagerAndDCAspect {
    @Autowired
    private UserArchiveMapper userArchiveMapper;
    @Autowired
    private ComparePermission comparePermission;

    @Around("@annotation(requireManagerAndDCAnno)")
    public Object requireManagerAndDCAspect(
            ProceedingJoinPoint pj, RequireManagerAndDCAnno requireManagerAndDCAnno
    ) throws Throwable {
        String uuid;
        try {
            uuid = pj.getArgs()[requireManagerAndDCAnno.uuidIndex()].toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            return ResponseEntity.ok(new ErrorR().normal("方法参数不符合校验要求" + e));
        }
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (userArchive.getIdentity() == UserIdentityEnum.MANAGER) {
            if (comparePermission.has(userArchive.getPermission(), PermissionConst.DISCIPLINARY_COMMITTEE)) {
                return pj.proceed();
            }
        }
        return ResponseEntity.ok(new UserR().insufficientAccountPermission());
    }
}
