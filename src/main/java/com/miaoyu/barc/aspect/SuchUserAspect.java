package com.miaoyu.barc.aspect;

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

    @Around("@annotation(SuchUserAnno)")
    public Object suchUserAspect(ProceedingJoinPoint pj) throws Throwable {
        String uuid = pj.getArgs()[0].toString();
        UserArchiveModel userArchive = userArchiveMapper.selectByUuid(uuid);
        if (userArchive == null) {
            return ResponseEntity.ok(new UserR().noSuchUser());
        }
        return pj.proceed();
    }
}
