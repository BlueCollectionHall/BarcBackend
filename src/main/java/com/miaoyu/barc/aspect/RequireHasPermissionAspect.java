package com.miaoyu.barc.aspect;

import com.miaoyu.barc.annotation.RequireHasPermissionAnno;
import com.miaoyu.barc.permission.ComparePermission;
import com.miaoyu.barc.response.UserR;
import com.miaoyu.barc.user.mapper.UserArchiveMapper;
import com.miaoyu.barc.user.model.UserArchiveModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequireHasPermissionAspect {

    private final UserArchiveMapper userArchiveMapper;
    private final ComparePermission comparePermission;

    public RequireHasPermissionAspect(UserArchiveMapper userArchiveMapper, ComparePermission comparePermission) {
        this.userArchiveMapper = userArchiveMapper;
        this.comparePermission = comparePermission;
    }

    @Around("@annotation(anno)")
    public Object requireHasPermissionAspect(
            ProceedingJoinPoint pj, RequireHasPermissionAnno anno
    ) throws Throwable {
        String uuid = pj.getArgs()[anno.uuidIndex()].toString();
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (userArchive.getIdentity() == anno.identity()) {
            if (comparePermission.has(userArchive.getPermission(), anno.permission())) {
                return pj.proceed();
            }
        }
        return ResponseEntity.ok(new UserR().insufficientAccountPermission());
    }
}
