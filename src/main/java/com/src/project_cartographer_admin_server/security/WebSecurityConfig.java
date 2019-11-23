package com.src.project_cartographer_admin_server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebSecurity
@Profile("prod")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/css/**").permitAll()
      .antMatchers("/js/**").permitAll()
      .anyRequest().authenticated()

      .and()
      .formLogin()
      .loginPage("/login")
      .permitAll()

      .and()
      .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
      .permitAll();
  }

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(inMemoryUserDetailsManager());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    //TODO: turn on for web debugging
    //web.debug(true);
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