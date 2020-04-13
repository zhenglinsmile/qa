package top.fine.qa.controller;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.fine.qa.model.User;
import top.fine.qa.repository.UserRepository;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping()
public class IndexController {

    @Value("${github.login.authorize_url}")
    private String authorize_url;

    @Value("${github.login.client_id}")
    private String client_id;

    @Value("${github.login.client_secret}")
    private String client_secret;

    @Value("${github.login.scope}")
    private String scope;

    @Value("${github.login.state}")
    private String state;

    @Resource
    private UserRepository userRepository;

    @GetMapping
    public String greeting(HttpServletRequest request) {
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
        return "index";
    }

    @GetMapping(value = "login")
    public void login(HttpServletResponse response) throws IOException {

        String loginUrl = authorize_url + "?client_id=" + client_id + "&client_secret=" + client_secret + "&scope=" + scope + "&state=" + state;
        response.sendRedirect(loginUrl);
    }
}
