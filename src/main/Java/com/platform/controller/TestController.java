package com.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class TestController {

    @RequestMapping("/hello")
    public ModelAndView add() {
<<<<<<< HEAD
        ModelAndView mav = new ModelAndView("/hello.jsp");
=======
        ModelAndView mav = new ModelAndView("hello");
>>>>>>> github/v1.0
        mav.addObject("hello", "hello");
        return mav;
    }
}
