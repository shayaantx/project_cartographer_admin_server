package com.src.project_cartographer_admin_server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebSecurity
@Profile("simple-security")
public class SimpleSecurity extends CommonSecurity {
  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(inMemoryUserDetailsManager());
  }

  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    final Properties inMemoryUsers = new Properties();
    for (String user : users) {

      String[] split = user.split(",", 2);
      inMemoryUsers.put(split[0], split[1]);
    }
    return new InMemoryUserDetailsManager(inMemoryUsers);
  }

  @Value("#{'${users}'.split(';')}")
  private List<String> users;
}