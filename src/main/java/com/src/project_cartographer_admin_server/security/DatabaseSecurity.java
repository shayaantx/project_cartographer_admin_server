package com.src.project_cartographer_admin_server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Profile("database-security")
public class DatabaseSecurity extends CommonSecurity {
  @Autowired
  DataSource dataSource;

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
      .passwordEncoder(passwordEncoder())
      .usersByUsernameQuery("select username, password, enabled from admin_users where username=?")
      .authoritiesByUsernameQuery("select username, authority from admin_authorities where username=?");
  }
}
