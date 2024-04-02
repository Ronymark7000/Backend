package com.project.JewelHub.user.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserLoginDto {
    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(nullable = false, length = 100)
    @NotEmpty
    @Size(min = 5, message = "Atleast 5 character long")
    private String password;
}
