package com.miaoyu.barc.aspect;

import com.miaoyu.barc.annotation.SuchWorkAnno;
import com.miaoyu.barc.api.work.mapper.WorkMapper;
import com.miaoyu.barc.api.work.model.WorkModel;
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
public class SuchWorkAspect {
    @Autowired
    private WorkMapper workMapper;

    @Around("@annotation(anno)")
    public Object suchWorkAspect(ProceedingJoinPoint pj, SuchWorkAnno anno) throws Throwable {
        Object value = pj.getArgs()[anno.index()];
        ResponseEntity<J> responseSuchFalse = ResponseEntity.ok(new ResourceR().resourceSuch(false, null));
        return switch (anno.selectType()) {
            case "id" -> workMapper.selectById(value.toString()) == null ? responseSuchFalse : pj.proceed();
            case "model" ->
                    workMapper.selectById(((WorkModel) value).getId()) == null ? responseSuchFalse : pj.proceed();
            default -> ResponseEntity.ok(new ErrorR().normal("服务器参数错误！"));
        };
    }
}
