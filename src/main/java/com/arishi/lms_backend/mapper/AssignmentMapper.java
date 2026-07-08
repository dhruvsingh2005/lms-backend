package com.arishi.lms_backend.mapper;

import com.arishi.lms_backend.dto.AssignmentDto;
import com.arishi.lms_backend.entity.Assignment;
import com.arishi.lms_backend.entity.Course;

public class AssignmentMapper {
	
	public static Assignment toEntity(AssignmentDto assignmentDto, Course course) {
		Assignment assignment = new Assignment();
		
		assignment.setCourse(course);
		assignment.setDescription(assignmentDto.getDescription());
		assignment.setDueDate(assignmentDto.getDueDate());
		assignment.setSource(null);
		assignment.setTitle(assignmentDto.getTitle());
		
		return assignment;
	}
	
	public static AssignmentDto toAssignmentDto(Assignment assignment) {
		AssignmentDto assignmentDto = new AssignmentDto();
		assignmentDto.setId(assignment.getId());
		assignmentDto.setTitle(assignment.getTitle());
		assignmentDto.setDescription(assignment.getDescription());
		assignmentDto.setDueDate(assignment.getDueDate());
		assignmentDto.setAssignmentUrl("/api/v1/assignment/" + assignment.getId() + "/pdf");
		
		return assignmentDto;
	}
}
