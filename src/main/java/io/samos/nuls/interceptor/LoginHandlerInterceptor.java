package io.samos.nuls.interceptor;

import io.samos.nuls.entity.User;
import io.samos.utils.RightUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

    private static String[] IGNORE_URI = {"/passport/login", "/passport/logout","/nodes","/node","/search"};
    private static Logger log = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    // 目标方法执行之前


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //return true;
        boolean flag = false;
        String url = request.getRequestURL().toString();


        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            HttpSession session = request.getSession();

            Object obj = session.getAttribute(session.getId());//Constants.SESSION_USER
            if (null == obj) {//未登录
                log.error("session失效，当前url：" + url+",sessionID:"+session.getId());
                return false;
            }

        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        response.setHeader("Access-Control-Allow-Origin","*");
//        HttpSession session = request.getSession();
//        Object obj = session.getAttribute(session.getId());//Constants.SESSION_USER
//        if (null == obj) {//未登录
//            log.error("has no session ：" +session.getId());
//
//        } else {
//            log.error("has session ：" +session.getId()+ " ,obj:"+obj);
//
//        }

        super.postHandle(request, response, handler, modelAndView);


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
