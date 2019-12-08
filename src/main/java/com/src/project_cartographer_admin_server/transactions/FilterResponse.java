package com.src.project_cartographer_admin_server.transactions;

import java.util.List;

public class FilterResponse {
  private List<DisplayRow> users;

  public FilterResponse(List<DisplayRow> users) {
    this.users = users;
  }

  public List<DisplayRow> getUsers() {
    return users;
  }
}
