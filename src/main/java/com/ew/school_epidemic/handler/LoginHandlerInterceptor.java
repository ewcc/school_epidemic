package com.ew.school_epidemic.handler;

import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ew
 * @date 2022/2/28 16:22
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    //在目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //防止拦截静态资源
        String uri = request.getRequestURI();
        if (uri.endsWith("js")||uri.endsWith("css")||uri.endsWith("jpg")||
                uri.endsWith("png")||uri.endsWith("svg")||uri.endsWith("ttf")||
                uri.endsWith("eot")||uri.endsWith("woff")){
            //放行
            return true ;
        }
        Object user =request.getSession().getAttribute("name");
        if(user == null){
            //未登录，进行拦截，返回登陆界面
            request.setAttribute("msg","没有权限请先进行登陆操作");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }else {
            //已登录，放行
            return true;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
