package com.arishi.lms_backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAssignmentDto {

    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;
}
