package top.fine.qa.controller;

import lombok.Data;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.fine.qa.dto.QuestionDto;
import top.fine.qa.model.Question;
import top.fine.qa.model.User;
import top.fine.qa.repository.QuestionRepository;
import top.fine.qa.repository.UserRepository;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-09 17:00
 **/
@Controller
@RequestMapping(value = "publish")
public class PublishController {

    @Resource
    private UserRepository userRepository;
    @Resource
    private QuestionRepository questionRepository;

    @GetMapping
    public String publishPage(Model model) {
        model.addAttribute("question",new QuestionDto());
        return "publish";
    }


    @PostMapping
    public String publish(@Valid @ModelAttribute QuestionDto question, Errors errors, HttpServletRequest request, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "publish";
        }
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
        } else {
            return "publish";
        }
        return "publish";
    }
}

