package top.fine.qa.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.fine.qa.dto.GithubAuthorizeDto;
import top.fine.qa.dto.GithubUser;

import java.io.IOException;

/**
 * @description: description
 * @author: zhengLin
 * @version: 1.0
 * @create: 2020-04-07 10:43
 **/
@Component
public class GithubAuthorizeProvider {

    @Value("${github.login.access_token_url}")
    private String access_token_url;

    @Value("${github.login.client_id}")
    private String client_id;

    @Value("${github.login.client_secret}")
    private String client_secret;

    @Value("${github.login.redirect_uri}")
    private String redirect_uri;

    @Value("${github.login.github_user_uri}")
    private String github_user_uri;

    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.get("application/json; charset=utf-8");


    public String getAccessToken(String code, String state) {
        GithubAuthorizeDto dto = new GithubAuthorizeDto();
        dto.setClient_id(client_id);
        dto.setClient_secret(client_secret);
        dto.setCode(code);
        dto.setRedirect_uri(redirect_uri);
        dto.setState(state);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(access_token_url)
                .post(RequestBody.create(JSON.toJSONString(dto), MEDIA_TYPE_JSON))
                .addHeader("Accept", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return String.valueOf(JSON.parseObject(response.body().string()).get("access_token"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(github_user_uri + "?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            GithubUser githubUser = JSON.parseObject(response.body().string(), GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
