package top.fine.qa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.fine.qa.dto.QuestionForm;
import top.fine.qa.model.Question;
import top.fine.qa.model.User;
import top.fine.qa.repository.QuestionRepository;
import top.fine.qa.repository.UserRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-09 17:00
 **/
@Controller
@RequestMapping(value = "question")
public class QuestionController {

    @Resource
    private QuestionRepository questionRepository;

    @GetMapping
    public String publishPage(Model model) {
        model.addAttribute(new QuestionForm());
        return "publish";
    }

}

