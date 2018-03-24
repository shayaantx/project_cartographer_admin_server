package com.src.project_cartographer_admin_server.transactions;

import java.util.List;

/**
 * Created by shayaantx on 3/24/2018.
 */
public class FilterResponse {
    public FilterResponse(List<DisplayRow> users) {
        this.users = users;
    }

    public List<DisplayRow> getUsers() {
        return users;
    }

    private List<DisplayRow> users;
}
