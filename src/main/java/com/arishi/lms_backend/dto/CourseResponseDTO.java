package com.arishi.lms_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {

    private Long id;

    private String title;

    private String description;

    private Long instructorId;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal price;

}
