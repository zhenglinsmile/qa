package top.fine.qa.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping
    public String greeting() {
        return "index";
    }

    @GetMapping(value = "login")
    public String login() {
        String loginUrl = authorize_url + "?client_id=" + client_id + "&client_secret=" + client_secret + "&scope=" + scope + "&state=" + state;
        return "forward:" + loginUrl;
    }
}
