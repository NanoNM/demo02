package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class publishController {
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
}