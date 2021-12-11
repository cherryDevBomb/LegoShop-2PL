package com.ubb.legoshop.rest.model;

import lombok.Data;

@Data
public class CustomerRequestModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
