package com.authentication.payload;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class UserPayload {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String phoneNumber;
    private Date dob;
    private String state;
    private String lga;
    private String city;
    private String photo;
    private String address;
    private Boolean isDeleted;
    private Long roleId;
}
