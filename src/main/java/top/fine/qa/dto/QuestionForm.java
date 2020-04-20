package top.fine.qa.dto;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

@Data
public class QuestionForm {

    @NotBlank(message = "title is blank")
    private String title;

    @NotBlank(message = "description is blank")
    private String description;

    @NotBlank(message = "tag is blank")
    private String tag;

    private String error;

}
