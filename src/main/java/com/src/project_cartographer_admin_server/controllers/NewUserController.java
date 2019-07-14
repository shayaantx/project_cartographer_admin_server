package com.src.project_cartographer_admin_server.controllers;

import com.src.project_cartographer_admin_server.businessobjects.NewUserBO;
import com.src.project_cartographer_admin_server.transactions.EditNewUserEmailRequest;
import com.src.project_cartographer_admin_server.transactions.ResendNewUserEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewUserController {
  @RequestMapping(value = "resendNewUserEmail", method = RequestMethod.POST)
  @ResponseBody
  public boolean resendActivationEmail(@RequestBody ResendNewUserEmailRequest request) {
    return newUserBO.resendActivationEmail(request);
  }

  @RequestMapping(value = "/editNewUserEmail", method = RequestMethod.POST)
  @ResponseBody
  public boolean editNewUserEmail(@RequestBody EditNewUserEmailRequest request) {
    return newUserBO.editEmail(request);
  }

  @RequestMapping(value = "/newUsers")
  public ModelAndView getNewUsers() {
    return newUserBO.getNewUsers();
  }

  @RequestMapping(value = "/activateUser")
  public ModelAndView activateUser(@RequestParam(value = "validationToken") String validationToken) {
    return newUserBO.activateUser(validationToken);
  }

  @Autowired
  private NewUserBO newUserBO;
}
