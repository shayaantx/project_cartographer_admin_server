package com.src.project_cartographer_admin_server.businessobjects;

import com.src.project_cartographer_admin_server.dataaccessobjects.AdminUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AdminUserBO {
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AdminUserDAO adminUserDAO;

  @Transactional
  public boolean changePassword(String newPassword) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    adminUserDAO.loadEntity(auth.getName()).setPassword(passwordEncoder.encode(newPassword));
    return true;
  }
}
