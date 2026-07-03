package com.arishi.lms_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50,
            message = "First name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z]+$",message = "firstname must contain only letters")
    private String firstName;

    @Size(min=2, max = 50,
            message = "Lastname cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z]+$",message = "lastname must contain only letters")
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")
    @Size(min = 8,
            message = "Password must be greater than 8 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Mobile number must be a valid 10-digit")
    private String mobileNumber;
}