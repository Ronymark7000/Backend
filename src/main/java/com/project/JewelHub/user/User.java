package com.project.JewelHub.user;

import com.project.JewelHub.user.dtos.UserRegisterDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
@Table(name = "jeweluser")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(nullable = false, length = 20, unique = true)
    @NotEmpty
    @Size(min =3, message = "Must have at least 3 Character")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "First name should not contain space")
    private String firstname;

    @Column(nullable = false, length = 20, unique = true)
    @NotEmpty
    @Size(min =3, message = "Must have at least 3 Character")
//    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Last name should not contain space")
    private String lastname;

    @Column(nullable = false, length = 10)
//    @Size(min=9, max =10, message = "Contact Number cannot be more than 10 numbers")
//    @NotBlank(message = "Contact number is mandatory")
    private long contact;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message = "Invalid email format")
    @NaturalId(mutable = true)
    private String email;

    @Column(nullable = false, length = 100)
    @NotEmpty
    @Size(min = 5, message = "Atleast 5 character long")
    private String password;

    @Column(nullable = false, length = 10)
//  @NotBlank(message = "Role is mandatory")
    private String role;

    //Line for Token Confirmation
    private boolean enabled;
    //============================//

    public User(UserRegisterDto userRegisterDto) {
        this.firstname = userRegisterDto.getFirstname();
        this.lastname = userRegisterDto.getLastname();
        this.contact = userRegisterDto.getContact();
        this.email = userRegisterDto.getEmail();
        this.password = userRegisterDto.getPassword();
        this.role = userRegisterDto.getRole();
        this.enabled = userRegisterDto.isEnabled();
    }

    public User(int userId){
            this.userId = userId;
    }

}
