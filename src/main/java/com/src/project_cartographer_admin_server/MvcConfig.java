package com.src.project_cartographer_admin_server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/index").setViewName("index");
    registry.addViewController("/profile").setViewName("profile");
    registry.addViewController("/newUsers").setViewName("newUsers");
    registry.addViewController("/users").setViewName("users");
    registry.addViewController("/user").setViewName("user");
    registry.addViewController("/emails").setViewName("emails");
    registry.addViewController("/fragments/navigationPanel").setViewName("navigationPanel");
    registry.addViewController("/fragments/bannerPanel").setViewName("bannerPanel");
    registry.addViewController("/fragments/csrf").setViewName("csrf");
  }
}