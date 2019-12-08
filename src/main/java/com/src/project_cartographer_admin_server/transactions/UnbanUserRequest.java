package com.src.project_cartographer_admin_server.transactions;

public class UnbanUserRequest {
  private int id;
  private String comments;

  public UnbanUserRequest() {
  }

  public int getId() {
    return id;
  }

  public String getComments() {
    return comments;
  }
}
