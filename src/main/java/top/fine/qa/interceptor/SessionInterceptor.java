package top.fine.qa.interceptor;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Filter;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.fine.qa.model.Question;
import top.fine.qa.model.User;
import top.fine.qa.repository.UserRepository;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-20 16:37
 **/
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置 context 级别的属性
        request.getServletContext().setAttribute("redirectUri", "login");
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if ("userToken".equalsIgnoreCase(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userRepository.findUserByToken(token);
                    if (null != user) {
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
