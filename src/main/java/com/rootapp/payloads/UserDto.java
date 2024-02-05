package com.rootapp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    @NotBlank(message = "name must not be blank")
    @Size(min = 6, message = "name must be minimum of 6 characters")
    private String name;

    @NotBlank(message = "name must not be blank")
    @Email(message = "email address is not valid")
    private String email;

    @NotBlank(message = "password must not be blank")
    @Size(min = 8, message = "password must be minimum of 8 characters")
    private String password;

    private String about;
    private String image;

}
