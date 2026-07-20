package com.arishi.lms_backend.dto.request;


import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank(message = "Course title is required.")
    @Size(min = 5, max = 50, message = "Course title must be between 5 and 50 characters.")
    private String title;

    @NotBlank(message = "Course description is required.")
    @Size(min = 20, max = 500, message = "Description must be between 20 and 500 characters.")
    private String description;

    @NotNull(message = "Start date is required.")
    @Future(message = "Start date must be in the future.")
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    @Future(message = "End date must be in the future.")
    private LocalDate endDate;

    @AssertTrue(message = "End date must be after start date.")
    public boolean isValidDateRange() {

        if (startDate == null || endDate == null) {
            return true;
        }

        return endDate.isAfter(startDate);
    }

    @NotBlank(message = "Course content link is required.")
    @Pattern(
            regexp = "^(https?|ftp)://.*$",
            message = "Please provide a valid URL."
    )
    @Size(max = 500, message = "Course content URL cannot exceed 500 characters.")
    private String courseContent;

    @NotNull(message = "Price is required.")
    @Digits(
            integer = 8,
            fraction = 2,
            message = "Price can have up to 8 digits and 2 decimal places."
    )
    private BigDecimal price;
}