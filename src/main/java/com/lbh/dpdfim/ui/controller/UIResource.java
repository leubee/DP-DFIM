package com.lbh.dpdfim.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class UIResource {
    @RequestMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/fpChart")
    public String fpChart() {
        return "echart-min";
    }
}
