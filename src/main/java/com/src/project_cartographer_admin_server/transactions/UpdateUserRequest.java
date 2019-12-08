package com.src.project_cartographer_admin_server.transactions;

public class UpdateUserRequest {
  int id;
  String username;
  String email;
  String userType;
  String comments;

  public UpdateUserRequest() {
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getUserType() {
    return userType;
  }

  public String getComments() {
    return comments;
  }
}
