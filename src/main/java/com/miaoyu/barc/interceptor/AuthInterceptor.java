package com.miaoyu.barc.interceptor;

import com.miaoyu.barc.annotation.IgnoreAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

// 全局路径Authorization令牌实现拦截器
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 放行静态合法请求
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 放行阻挠注解
        if (method.isAnnotationPresent(IgnoreAuth.class) ||
                handlerMethod.getBeanType().isAnnotationPresent(IgnoreAuth.class)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("账号未登录！Accept without Authorization!");
        return false;
    }
}
