package com.example.demo.provider;


import com.alibaba.fastjson.JSON;
import com.example.demo.resouce.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import com.example.demo.resouce.GitHubOTO;

import java.io.IOException;


@Component
public class GitHubProvider{
    public String getAsscToken(GitHubOTO gitHubOTO){
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(gitHubOTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
           String tokken = (string.split("&")[0]).split("=")[1];
            return tokken;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getuser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);

            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}