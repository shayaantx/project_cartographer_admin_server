package com.src.project_cartographer_admin_server.controllers;

import com.src.project_cartographer_admin_server.businessobjects.UserBO;
import com.src.project_cartographer_admin_server.transactions.FilterRequest;
import com.src.project_cartographer_admin_server.transactions.FilterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by shayaantx on 1/21/2018.
 */
@Controller
public class UserController {

  @RequestMapping(value = "/users")
  public ModelAndView mainPage() {
    return userBO.getScreenConfig();
  }

  @RequestMapping(value = "/searchForUser", method = RequestMethod.POST)
  @ResponseBody
  public FilterResponse searchForUser(@RequestBody FilterRequest filterRequest) {
    return userBO.searchForUser(filterRequest.getFilterText());
  }

  @RequestMapping(value = "/user")
  public ModelAndView userPage(@RequestParam(value = "id", required = false) Integer userIdParam,
                               @RequestParam(value = "username", required = false) String username,
                               @RequestParam(value = "email", required = false) String email) {
    return userBO.loadUser(userIdParam, username, email);
  }

  @RequestMapping(value = "/newUsers")
  public ModelAndView getNewUsers() {
    return userBO.getNewUsers();
  }

  @RequestMapping(value = "/banUser")
  public ModelAndView banUser(@RequestParam(value = "id") Integer userIdParam, @RequestParam(value = "comments", required = false) String comments) {
    return userBO.banUser(userIdParam, comments);
  }

  @RequestMapping(value = "/unbanUser")
  public ModelAndView unbanUser(@RequestParam(value = "id") Integer userIdParam, @RequestParam(value = "comments", required = false) String comments) {
    return userBO.unbanUser(userIdParam, comments);
  }

  @RequestMapping(value = "/banMachine")
  public ModelAndView banMachine(@RequestParam(value = "userId") Integer userIdParam, @RequestParam(value = "machineId") Integer machineId) {
    return userBO.banMachine(userIdParam, machineId);
  }

  @RequestMapping(value = "/banAllMachines")
  public ModelAndView banAllMachines(@RequestParam(value = "userId") Integer userIdParam) {
    return userBO.banAllMachines(userIdParam);
  }

  @RequestMapping(value = "/unbanMachine")
  public ModelAndView unbanMachine(@RequestParam(value = "userId") Integer userIdParam, @RequestParam(value = "machineId") Integer machineId) {
    return userBO.unbanMachine(userIdParam, machineId);
  }

  @RequestMapping(value = "/updateUser")
  public ModelAndView updateUser(@RequestParam(value = "id") Integer userIdParam,
                                 @RequestParam(value = "username", required = false) String username,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "userType", required = false) String userType,
                                 @RequestParam(value = "comments", required = false) String comments) {
    return userBO.updateUser(userIdParam, username, email, userType, comments);
  }

  @RequestMapping(value = "/activateUser")
  public ModelAndView activateUser(@RequestParam(value = "validationToken") String validationToken) {
    return userBO.activateUser(validationToken);
  }


  @Autowired
  private UserBO userBO;
}
