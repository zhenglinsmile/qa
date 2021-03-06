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
@RequestMapping(value = "publish")
public class PublishController {

    @Resource
    private UserRepository userRepository;
    @Resource
    private QuestionRepository questionRepository;

    @GetMapping
    public String publishPage(Model model) {
        model.addAttribute(new QuestionForm());
        return "publish";
    }


    @PostMapping
    public String publish(@Valid @ModelAttribute QuestionForm questionForm, Errors errors, HttpServletRequest request, Model model) {
        if (errors.hasErrors()) {
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("userNoLoginMsg", "User is not login");
            return "publish";
        }
        Question question = new Question();
        question.setName(questionForm.getTitle());
        question.setDescription(questionForm.getDescription());
        question.setTag(questionForm.getTag());
        question.setAuthor(user.getAccountId());
        question.setCreateTime(System.currentTimeMillis());
        question.setModifyTime(question.getCreateTime());
        questionRepository.save(question);
        return "redirect:/";
    }
}

