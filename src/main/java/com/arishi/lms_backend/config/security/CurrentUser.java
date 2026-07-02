package com.arishi.lms_backend.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {
	
	public static Long getId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserInfo) principal).getId();
	}
	
	public static String getRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserInfo) principal).getRole();
	}
	
	public static UserInfo getUserInfo(Long id, String role) {
		return new UserInfo(id, role);
	}
}

class UserInfo {
	
	private Long id;
	
	private String role; 
	
	public UserInfo(Long id, String role) {
		this.id = id;
		this.role = role;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getRole() {
		return this.role;
	}
}