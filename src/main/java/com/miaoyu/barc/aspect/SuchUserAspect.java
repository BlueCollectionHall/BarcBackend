package com.miaoyu.barc.aspect;

import com.miaoyu.barc.annotation.SuchUserAnno;
import com.miaoyu.barc.response.UserR;
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
public class SuchUserAspect {
    @Autowired
    private UserArchiveMapper userArchiveMapper;

    @Around("@annotation(anno)")
    public Object suchUserAspect(
            ProceedingJoinPoint pj,
            SuchUserAnno anno
    ) throws Throwable {
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(pj.getArgs()[anno.uuidIndex()].toString());
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        return pj.proceed();
    }
}
