package com.src.project_cartographer_admin_server.controllers;

import com.src.project_cartographer_admin_server.businessobjects.AdminUserBO;
import com.src.project_cartographer_admin_server.transactions.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
  @Autowired
  private Environment environment;
  @Autowired
  private AdminUserBO adminUserBO;

  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public ModelAndView mainPage(Authentication authentication) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("profile");
    mav.getModel().put("username", authentication.getName());
    mav.getModel().put("showChangePassword", environment.acceptsProfiles("database-security"));
    return mav;
  }

  @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
  @ResponseBody
  public boolean changePassword(@RequestBody ChangePasswordRequest newPasswordRequest) {
    return adminUserBO.changePassword(newPasswordRequest.getNewPassword());
  }
}
