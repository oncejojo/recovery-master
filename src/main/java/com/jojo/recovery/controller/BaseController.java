package com.jojo.recovery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author JoJo     
 * @Date 2022/8/4 16:46
 **/
@Controller
public class BaseController {

    @GetMapping
    public String index() {
        return "login";
    }


}

