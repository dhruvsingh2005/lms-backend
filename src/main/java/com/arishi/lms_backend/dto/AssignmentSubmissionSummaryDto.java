package com.arishi.lms_backend.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmissionSummaryDto {
	
    private Long studentId;
    
    private Long submissionId;
    
    private String studentName;
    
    private ZonedDateTime submissionDate;
    
    private String submissionUrl;
}
