package com.miaoyu.barc.aspect;

import com.miaoyu.barc.annotation.SuchWorkCommentAnno;
import com.miaoyu.barc.comment.mapper.WorkCommentMapper;
import com.miaoyu.barc.comment.pojo.WorkCommentPojo;
import com.miaoyu.barc.response.ErrorR;
import com.miaoyu.barc.response.ResourceR;
import com.miaoyu.barc.utils.J;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SuchWorkCommentAspect {
    @Autowired
    private WorkCommentMapper workCommentMapper;

    @Around("@annotation(anno)")
    public Object suchWorkCommentAspect(ProceedingJoinPoint pj, SuchWorkCommentAnno anno) throws Throwable {
        String value = pj.getArgs()[anno.index()].toString();
        ResponseEntity<J> responseSuchFalse = ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        return switch (anno.selectType()) {
            case "id" -> workCommentMapper.selectById(value) == null ? responseSuchFalse : pj.proceed();
            case "work_id" -> workCommentMapper.selectByWorkId(value).isEmpty() ? responseSuchFalse : pj.proceed();
            default -> ResponseEntity.ok(new ErrorR().normal("服务器参数出错！"));
        };
    }
}
