package top.fine.qa.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.fine.qa.dto.GithubUser;
import top.fine.qa.model.User;
import top.fine.qa.provider.GithubAuthorizeProvider;
import top.fine.qa.repository.UserRepository;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-07 14:04
 **/
@Controller
@Slf4j
@RequestMapping(value = "github")
public class AuthorizeController {

    @Resource
    private GithubAuthorizeProvider authorizeProvider;

    @Resource
    private UserRepository userRepository;

    @GetMapping
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        String accessToken = authorizeProvider.getAccessToken(code, state);
        if (StringUtils.isNotBlank(accessToken)) {
            GithubUser githubUser = authorizeProvider.getGithubUser(accessToken);
            if (null != githubUser) {
                Long accountId = githubUser.getId();
                User userByAccountId = userRepository.findUserByAccountId(accountId);
                User user = new User();
                if (userByAccountId != null) {
                    user.setId(userByAccountId.getId());
                }
                user.setAccountId(accountId);
                user.setName(githubUser.getLogin());
                String userToken = UUID.randomUUID().toString().replaceAll("-", "");
                user.setToken(userToken);
                user.setCreateTime(System.currentTimeMillis());
                user.setModifyTime(user.getCreateTime());
                userRepository.save(user);
                Cookie cookie = new Cookie("userToken", userToken);
                cookie.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie);
            }
        }
        return "redirect:/";
    }

}
