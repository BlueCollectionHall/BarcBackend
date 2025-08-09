package com.miaoyu.barc.aspect;

import com.miaoyu.barc.annotation.RequireSelfOrManagerAnno;
import com.miaoyu.barc.permission.ComparePermission;
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
public class RequireSelfOrManagerAspect {
    @Autowired
    private UserArchiveMapper userArchiveMapper;
    @Autowired
    private ComparePermission comparePermission;

    @Around("@annotation(anno)")
    public Object requireItemSelfOrManagerAspect(
            ProceedingJoinPoint pj,
            RequireSelfOrManagerAnno anno
    ) throws Throwable {
        Object [] args = pj.getArgs();
        validateArgs(anno, args);
        String uuid = args[anno.uuidIndex()].toString();
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        if (isAuthor(uuid, args[anno.authorUuidIndex()].toString()) || isManager(userArchive, anno.managerPermission(), anno.isHasElseUpper())) {
            return pj.proceed();
        }
        return ResponseEntity.ok(new UserR().insufficientAccountPermission());
    }

    private void validateArgs(RequireSelfOrManagerAnno check, Object[] args) {
        if (check.uuidIndex() < 0 || check.uuidIndex() >= args.length) {
            throw new IllegalArgumentException("用户UUID参数索引越界");
        }
//        if (check.workIndex() < 0 || check.workIndex() >= args.length) {
//            throw new IllegalArgumentException("作品参数索引越界");
//        }
//        if (!(args[check.workIndex()] instanceof WorkModel)) {
//            throw new IllegalArgumentException("作品参数必须实现WorkModel接口");
//        }
    }

    private boolean isAuthor(String basicUuid, String authorUuid) {
        System.out.println(basicUuid + " +++ " + authorUuid);
        return basicUuid.equals(authorUuid);
    }

    private boolean isManager(UserArchiveModel userArchive, int permission, boolean isHasElseUpper) {
        if (userArchive.getIdentity().equals(UserIdentityEnum.MANAGER)) {
            if (isHasElseUpper) {
                return comparePermission.has(userArchive.getPermission(), permission);
            }
            return comparePermission.compare(userArchive.getPermission(), permission);
        }
        return false;
    }
}
