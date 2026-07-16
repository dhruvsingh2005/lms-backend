package com.arishi.lms_backend.dto;

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

public class StudentUpdateProfileDTO {


    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50,
            message = "First name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z]+$", message = "firstname must contain only letters")
    private String firstName;

    @Size(min = 2, max = 50,
            message = "Lastname cantain 2 to 50 characters")
    @Pattern(
            regexp = "^$|^[a-zA-Z]{2,50}$",
            message = "Last name must contain only letters and be between 2 and 50 characters"
    )
    private String lastName;
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

