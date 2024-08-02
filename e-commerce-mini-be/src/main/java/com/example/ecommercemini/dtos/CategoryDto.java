package com.example.ecommercemini.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @NotBlank(message = "Please provide a valid title")
    @Length(min = 2, message = "Please add minimum of 2 characters in title")
    private String categoryTitle;

    @NotBlank(message = "Please provide a valid description")
    private String description;
    private String categoryImage;

}
