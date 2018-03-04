package com.src.project_cartographer_admin_server.controllers;

import com.src.project_cartographer_admin_server.businessobjects.UserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by shayaantx on 1/21/2018.
 */
@Controller
public class UserController {

    @RequestMapping(value="/users")
    public ModelAndView mainPage() {
        return userBO.getScreenConfig();
    }

    @RequestMapping(value="/user")
    public ModelAndView userPage(@RequestParam(value = "id", required = false) Integer userIdParam,
                                 @RequestParam(value = "username", required = false) String username,
                                 @RequestParam(value = "email", required = false) String email) {
        return userBO.loadUser(userIdParam, username, email);
    }

    @RequestMapping(value="/banUser")
    public ModelAndView banUser(@RequestParam(value = "id") Integer userIdParam) {
        return userBO.banUser(userIdParam);
    }

    @RequestMapping(value="/unbanUser")
    public ModelAndView unbanUser(@RequestParam(value = "id") Integer userIdParam) {
        return userBO.unbanUser(userIdParam);
    }

    @Autowired
    private UserBO userBO;
}
