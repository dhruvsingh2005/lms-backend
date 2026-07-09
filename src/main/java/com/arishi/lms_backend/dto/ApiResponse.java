package com.arishi.lms_backend.dto;

import java.util.List;

public class ApiResponse {

	private Integer statusCode;
	
	private List<String> message;
	
	private Object data;

	public ApiResponse(Integer statusCode, List<String> message, Object data) {
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public List<String> getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
}
