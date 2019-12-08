package com.src.project_cartographer_admin_server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "admin_users")
public class AdminUser {

  @Id
  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;
  @Column(name = "enabled")
  private boolean enabled;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
