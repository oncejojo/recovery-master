package com.jojo.recovery.common.handle;


import com.jojo.recovery.model.SysUser;
import com.jojo.recovery.utils.AdminUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "User-Agent";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o)
            throws Exception {

        // 请求URL不包含域名
        String uri = request.getRequestURI();
        SysUser user = AdminUtil.getLoginUser(request);
        // 请求拦截处理

        if (uri.startsWith("/admin")
                && !uri.startsWith("/admin/login")
                && null == user
                && !uri.startsWith("/api")
                && !uri.startsWith("/css")
                && !uri.startsWith("/images")
                && !uri.startsWith("/js")) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }

        // 返回true才会执行postHandle
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o,
            Exception e)
            throws Exception {
    }
}
