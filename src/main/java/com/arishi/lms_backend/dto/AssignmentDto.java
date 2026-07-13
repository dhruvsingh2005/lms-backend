package com.arishi.lms_backend.dto;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDto {

	private Long id;
	
	@NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
	private String title;
	
	@NotBlank(message = "Description cannot be empty")
    @Size(min = 1, max = 200, message = "Description must be between 1 and 200 characters")
	private String description;
	
	@NotNull(message = "Due date cannot be empty")
	private LocalDate dueDate;
	
    @JsonIgnore
    @NotNull(message = "Assignment file is required")
	private MultipartFile assignmentSource;
	
	private String assignmentUrl;
}
