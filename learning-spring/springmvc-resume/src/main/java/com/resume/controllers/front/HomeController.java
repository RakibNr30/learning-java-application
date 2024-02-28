package com.resume.controllers.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @ModelAttribute
    public void commonData(Model model) {
        model.addAttribute("title", "Home");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "front/home/index";
    }
}