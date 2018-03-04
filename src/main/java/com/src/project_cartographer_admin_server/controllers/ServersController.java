package com.src.project_cartographer_admin_server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by shayaantx on 3/3/2018.
 */
@Controller
public class ServersController {
    @RequestMapping(value="/servers")
    public ModelAndView mainPage() {
        return new ModelAndView("servers");
    }

}
