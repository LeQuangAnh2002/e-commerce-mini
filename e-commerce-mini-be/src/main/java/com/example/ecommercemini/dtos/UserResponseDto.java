package com.example.ecommercemini.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    @Size(max = 100, message = "First name cant be longer than 100 characters")
    @NotBlank(message = "Please provide a first name")
    private String firstName;

    @Size(max = 100, message = "Last name cant be longer than 100 characters")
    @NotBlank(message = "Please provide a last name")
    private String lastName;

    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Please provide an email")
    private String email;

    @Size(min = 10, max = 10, message = "Please provide a valid 10 digit phone number")
    private String phone;

    private String address;


    @Size(max = 150, message = "City name cant be longer than 150 characters")
    private String city;
    @Size(max = 100, message = "Province name cant be longer than 100 characters")
    private String province;


//	@ImageNameValid
    private String image;


    private Set<RoleDto> roles = new HashSet<>();
}
