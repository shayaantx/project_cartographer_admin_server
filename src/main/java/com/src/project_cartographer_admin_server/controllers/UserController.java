package com.src.project_cartographer_admin_server.controllers;

import com.src.project_cartographer_admin_server.businessobjects.UserBO;
import com.src.project_cartographer_admin_server.transactions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

  @Autowired
  private UserBO userBO;

  @RequestMapping(value = "/users")
  public ModelAndView mainPage() {
    return userBO.getScreenConfig();
  }

  @RequestMapping(value = "/user")
  public ModelAndView userPage(@RequestParam(value = "id", required = false) Integer userIdParam,
                               @RequestParam(value = "username", required = false) String username,
                               @RequestParam(value = "email", required = false) String email) {
    return userBO.loadUser(userIdParam, username, email);
  }

  @RequestMapping(value = "/searchForUser", method = RequestMethod.POST)
  @ResponseBody
  public FilterResponse searchForUser(@RequestBody FilterRequest filterRequest) {
    return userBO.searchForUser(filterRequest.getFilterText());
  }

  @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
  @ResponseBody
  public UserResponse updateUser(@RequestBody UpdateUserRequest request) {
    return userBO.updateUser(request.getId(), request.getUsername(), request.getEmail(), request.getUserType(), request.getComments());
  }

  @RequestMapping(value = "/banUser")
  @ResponseBody
  public UserResponse banUser(@RequestBody BanUserRequest request) {
    return userBO.banUser(request);
  }

  @RequestMapping(value = "/unbanUser")
  @ResponseBody
  public UserResponse unbanUser(@RequestBody UnbanUserRequest request) {
    return userBO.unbanUser(request);
  }
}
