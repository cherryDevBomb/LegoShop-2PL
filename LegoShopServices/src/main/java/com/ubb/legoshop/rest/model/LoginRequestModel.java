package com.ubb.legoshop.rest.model;

import lombok.Data;

@Data
public class LoginRequestModel {

    private String email;
    private String password;
}
