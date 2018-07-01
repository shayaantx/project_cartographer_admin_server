package com.src.project_cartographer_admin_server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shayaantx on 7/1/2018.
 */
@Entity
public class NewUser {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValidationToken() {
        return validationToken;
    }

    public void setValidationToken(String validationToken) {
        this.validationToken = validationToken;
    }


    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "validation_token")
    private String validationToken;
}