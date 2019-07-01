package com.example.demo.Controller;

import com.example.demo.provider.GitHubProvider;
import com.example.demo.resouce.SQLinsert;
import com.example.demo.resouce.GitHubOTO;
import com.example.demo.resouce.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用来跳转到callback并获取get方法中的code和state
 */

@Controller
public class GitHubBackCodeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    private  SQLinsert SQLinsert;

    @Value("${GitHubBackCodeController.Client}")
    private String client;
    @Value("${GitHubBackCodeController.secret}")
    private String secret;
    @Value("${GitHubBackCodeController.Redirect_uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        GitHubOTO gitHubOTO = new GitHubOTO();
        gitHubOTO.setClient_id(client);
        gitHubOTO.setClient_secret(secret);
        gitHubOTO.setCode(code);
        gitHubOTO.setState(state);
        gitHubOTO.setRedirect_uri(redirect_uri);
        String tokken = gitHubProvider.getAsscToken(gitHubOTO);
        GithubUser githubUser = gitHubProvider.getuser(tokken);
        String user =githubUser.getLogin();
        long ID = githubUser.getId();
        if(user!=null){
            request.getSession().setAttribute("user",user);
            SQLinsert.insertIntoUserinfo(ID,code,user,tokken);
            response.addCookie(new Cookie("token",tokken));
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
