package com.arishi.lms_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {

	private Integer statusCode;
	
	private List<String> message;
	
	private Object data;
}
