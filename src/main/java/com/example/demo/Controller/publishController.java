package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {
    @GetMapping("/publish")
    public String publish(HttpServletRequest request){
        request.getSession().setAttribute("error",null);
        request.getSession().setAttribute("success",null);
        return "publish";
    }
}
