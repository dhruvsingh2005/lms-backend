package com.arishi.lms_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentResponseDTO {

    private Long id;

    private String title;

    private String description;

    private String courseContent;

}
