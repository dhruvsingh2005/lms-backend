package com.arishi.lms_backend.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import lombok.AllArgsConstructor;
import lombok.Data;

public class CurrentUser {
	
	public static Long getId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserInfo) principal).getId();
	}
	
	public static String getRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserInfo userInfo = (UserInfo) principal;
		return userInfo.getRole();
	}
	
	public static UserInfo getUserInfo(Long id, String role) {
		return new UserInfo(id, role);
	}
}

@Data
@AllArgsConstructor
class UserInfo {
	
	private Long id;
	
	private String role;
}