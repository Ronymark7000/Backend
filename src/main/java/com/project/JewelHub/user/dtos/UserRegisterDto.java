package com.project.JewelHub.user.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @NotEmpty
    @Size(min =3, message = "Must have at least 3 Character")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "First name should not contain space")
    private String firstname;

    @NotEmpty
    @Size(min =3, message = "Must have at least 3 Character")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Last name should not contain space")
    private String lastname;

//   @Size(min=9, max =10, message = "Contact Number cannot be more than 10 numbers")
//   @NotBlank(message = "Contact number is mandatory")
    private long contact;


    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty
    @Size(min = 5, message = "Atleast 5 character long")
    private String password;

//  @NotBlank(message = "Role is mandatory")
    private String role;

    //==============================//
    public boolean isEnabled;
}
