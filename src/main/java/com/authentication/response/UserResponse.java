package com.authentication.response;


import com.authentication.model.Role;
import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
@Builder
public class UserResponse {
    private Long id;
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
    private Boolean enabled;
    private Set<Role> roles;
}
