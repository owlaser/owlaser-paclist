package com.owlaser.paclist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PacController {

    @ResponseBody
    @GetMapping(value = "/paclist")
    public static String show(){
        return "HelloWorld";
    }
}
