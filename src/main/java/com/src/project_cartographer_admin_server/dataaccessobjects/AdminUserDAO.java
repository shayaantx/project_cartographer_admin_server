package com.src.project_cartographer_admin_server.dataaccessobjects;

import com.src.project_cartographer_admin_server.models.AdminUser;
import org.springframework.stereotype.Component;

@Component
public class AdminUserDAO extends CommonDAO<AdminUser> {
  @Override
  public Class<AdminUser> getClazz() {
    return AdminUser.class;
  }
}
