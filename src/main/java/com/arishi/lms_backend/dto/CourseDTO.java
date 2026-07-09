package com.arishi.lms_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

	private Long id;

	@NotBlank(message = "Course title is required.")

	@Size(min = 5, max = 50, message = "Course title must be between 5 and 50 characters.")
	private String title;

	@NotBlank(message = "Course description is required.")
	@Size(min = 20, max = 1000, message = "Description must be between 20 and 1000 characters.")
	private String description;

	// @NotNull(message = "Instructor Id is required.")
	// @Positive(message = "Instructor Id must be greater than 0.")
	private Long instructorId;

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
	@Pattern(regexp = "^(https?|ftp)://.*$", message = "Please provide a valid URL.")
	@Size(max = 500)
	private String courseContent;

	@NotNull(message = "Price is required.")
	@Digits(integer = 8, fraction = 2, message = "Price must be greater than 0.")
	private BigDecimal price;
}