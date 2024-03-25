package com.project.JewelHub.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private long contact;

}
