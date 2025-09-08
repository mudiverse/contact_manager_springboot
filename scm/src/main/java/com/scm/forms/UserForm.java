package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must be greater than 3 chars")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must be greater than 5 chars")
    private String password;
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phoneNumber;
    @NotBlank(message = "About is required")
    private String about;

}
