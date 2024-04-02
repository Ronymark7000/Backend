package com.project.JewelHub.user.dtos;


import com.project.JewelHub.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

//@NoArgsConstructor
@Getter
@Setter
public class UserResDto {

    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private long contact;
    private String role;
    private boolean enabled;

    public UserResDto(User user) {
        this.userId = user.getUserId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.contact = user.getContact();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
    }
}
