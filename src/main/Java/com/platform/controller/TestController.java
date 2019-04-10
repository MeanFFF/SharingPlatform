package com.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class TestController {

    @RequestMapping("/hello")
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView("hello");
        mav.addObject("hello", "hello");
        return mav;
    }
}
