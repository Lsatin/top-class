package com.lsatin.topclass.webssm.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public String school() {
        return "school";
    }

    @RequestMapping(value = "/class", method = RequestMethod.GET)
    public String clazz() {
        return "class";
    }

    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public String teacher() {
        return "teacher";
    }

    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String student() {
        return "student";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

}
