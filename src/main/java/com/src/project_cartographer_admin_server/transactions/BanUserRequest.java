package com.src.project_cartographer_admin_server.transactions;

public class BanUserRequest {
  private int id;
  private String comments;

  public BanUserRequest() {
  }

  public int getId() {
    return id;
  }

  public String getComments() {
    return comments;
  }
}
