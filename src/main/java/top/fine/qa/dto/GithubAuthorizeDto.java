package top.fine.qa.dto;

import lombok.Data;

/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-07 10:44
 **/
@Data
public class GithubAuthorizeDto {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
