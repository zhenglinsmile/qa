package top.fine.qa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-09 17:00
 **/
@Controller
@RequestMapping(value = "publish")
public class PublishController {

    @GetMapping
    public String publishPage() {
        return "publish";
    }

}
