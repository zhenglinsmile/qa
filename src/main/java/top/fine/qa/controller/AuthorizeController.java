package top.fine.qa.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.fine.qa.dto.GithubUser;
import top.fine.qa.provider.GithubAuthorizeProvider;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @GetMapping(value = "callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
        String accessToken = authorizeProvider.getAccessToken(code, state);
        if (StringUtils.isNotBlank(accessToken)) {
            GithubUser githubUser = authorizeProvider.getGithubUser(accessToken);
            if (null != githubUser){
                request.getSession().setAttribute("user",githubUser);
            }
        }
        return "redirect:/";
    }

}
