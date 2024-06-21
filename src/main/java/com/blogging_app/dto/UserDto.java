package com.blogging_app.dto;

import com.blogging_app.entity.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private Long userId;

    @NotEmpty(message = "Name must not be empty!!")
    private String name;

    @Email(message = "Email address is not valid!!")
    @NotEmpty(message = "Email must not be empty!!")
    private String email;


    @NotEmpty(message = "Password must not be empty!!")
    private String password;

    @NotEmpty(message = "About must not be empty!!")
    private String about;

    Set<RoleDto> roles = new HashSet<>();
}
