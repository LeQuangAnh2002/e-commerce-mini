package com.example.ecommercemini.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int userId;
    @Size(max = 100, message = "First name cant be longer than 100 characters")
    @NotBlank(message = "Please provide a first name")
    private String firstName;

    @Size(max = 100, message = "Last name cant be longer than 100 characters")
    @NotBlank(message = "Please provide a last name")
    private String lastName;

    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Please provide an email")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,15}$", message = "Password should contain atleast one uppercase, one lowercase, one number and one special character")
    private String password;

    @Size(min = 10, max = 10, message = "Please provide a valid 10 digit phone number")
    private String phone;

    private String address;

    @Size(max = 150, message = "City name cant be longer than 150 characters")
    private String city;
    @Size(max = 100, message = "Province name cant be longer than 100 characters")
    private String province;

    private String image;

    private Set<RoleDto> roles = new HashSet<>();
}
