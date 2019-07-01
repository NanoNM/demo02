package com.example.demo.Controller;

import com.example.demo.resouce.SQLinsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 *跳转到主页
 */
@Controller
public class IndexPageController {
    String flag = null;
    @Autowired
    private SQLinsert sqLinsert;
    @GetMapping("/")
    public String seyhello(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals("token")){
                flag = sqLinsert.getCToken(cookie.getValue().trim());
            }
        }
        if (flag!=null){
            request.getSession().setAttribute("user",flag);
        }
        return "index";
    }

}
