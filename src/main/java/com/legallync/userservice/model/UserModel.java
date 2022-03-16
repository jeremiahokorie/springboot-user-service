package com.legallync.userservice.model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserModel {

    @Column(name = "firstName",nullable = false, unique = false)
    private String firstName;

    @Column(name = "lastName",nullable = false, unique = false)
    private String lastName;

    @Column(name = "email",nullable = false, unique = false)
    private String email;

    @Column(name = "phone",nullable = false, unique = false)
    private String phone;

    @Column(name = "userName",nullable = false, unique = true)
    private String userName;

    @Column(length = 60, name = "password",nullable = false, unique = false)
    private String password;

}
