package com.legallync.userservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userid;

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

   // @Column(name = "dob",nullable = false, unique = false)
    private Date dob;

  //  @Column(name = "zipCode",nullable = false, unique = false)
    private String zipcode;

   // @Column(name = "city",nullable = false, unique = false)
    private String city;

  //  @Column(name = "language",nullable = false, unique = false)
    private String language;

   // @Column(name = "message",nullable = false, unique = false)
    private String message;

   // @Column(name = "created",nullable = false, unique = false)
    private java.util.Date created;

   // @Column(name = "dateUpdated", nullable = false, unique = false)
    private Timestamp dateUpdated;

    @Column(name = "dateDeleted")
    private Timestamp dateDeleted;

    @Column(name = "status")
    private String status;

    @Column(name = "lastLoginDate")
    private Timestamp lastLoginDate;

    @Column(name = "lastLogoutDate")
    private Timestamp lastLogoutDate;

    private String role;

    private boolean enabled = false;

}
