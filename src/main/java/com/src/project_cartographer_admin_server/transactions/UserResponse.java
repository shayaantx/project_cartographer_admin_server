package com.src.project_cartographer_admin_server.transactions;

public class UserResponse {
  private final int id;
  private final String username;
  private final String email;
  private final String userType;
  private final String comments;
  private final boolean banned;

  public UserResponse(int id, String username, String email, String userType, String comments, boolean banned) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.userType = userType;
    this.comments = comments;
    this.banned = banned;
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

  public boolean getBanned() {
    return banned;
  }
}
