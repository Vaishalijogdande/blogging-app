package com.blogging_app.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private Long categoryId;

    @NotEmpty(message = "Title must not be empty!!")
    private String categoryTitle;

    @NotEmpty(message = "Description must not be empty!!")
    private String categoryDescription;
}
