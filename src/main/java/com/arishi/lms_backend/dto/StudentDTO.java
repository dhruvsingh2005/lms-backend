package com.arishi.lms_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
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
            message = "Lastname cantain 2 to 50 characters")
    @Pattern(
            regexp = "^$|^[a-zA-Z]{2,50}$",
            message = "Last name must contain only letters and be between 2 and 50 characters"
    )
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")
    @Size(min = 8,
            message = "Password must be greater than 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^(?=.{1,254}$)(?=.{1,64}@)(?!.*\\.\\.)(?![.])[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@(?:[A-Za-z]+\\.)+[A-Za-z]{2,}$",
            message = "Invalid email format"
    )
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Mobile number must be a valid 10-digit")
    private String mobileNumber;
}