package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.dto.request.LoginRequest;
import com.arishi.lms_backend.entity.Instructor;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.repo.InstructorRepository;
import com.arishi.lms_backend.repo.StudentRepository;
import com.arishi.lms_backend.service.AuthService;
import com.arishi.lms_backend.util.AuthUtils;
import com.arishi.lms_backend.util.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String ROLE_STUDENT = "student";
    private static final String ROLE_INSTRUCTOR = "instructor";

    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    @Override
    public void login(LoginRequest request, String role, HttpServletResponse response) {

        String normalizedRole = normalizeRole(role);
        String normalizedEmail = normalizeEmail(request.getEmail());

        LoginUser loginUser = getLoginUser(request.getEmail(), normalizedRole);

        validatePassword(request.getPassword(), loginUser.passwordHash());

        String accessToken = authUtils.getAccessToken(loginUser.id(), normalizedRole);

        ResponseCookie accessTokenCookie = CookieUtils.createAccessTokenCookie(accessToken);

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    }

    private String normalizeRole(String role) {

        if (role == null || role.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role is required");
        }

        return role.trim().toLowerCase();
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private LoginUser getLoginUser(String email, String role) {

        if (ROLE_STUDENT.equals(role)) {
            Student student = studentRepository.findByEmailAndDeletedAtIsNull(email).orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
            return new LoginUser(student.getId(), student.getPasswordHash());
        }

        if (ROLE_INSTRUCTOR.equals(role)) {
            Instructor instructor = instructorRepository.findByEmailAndDeletedAtIsNull(email).orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
            return new LoginUser(instructor.getId(), instructor.getPasswordHash());
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role");
    }

    private void validatePassword(String rawPassword, String passwordHash) {

        boolean isPasswordMatched = passwordEncoder.matches(rawPassword, passwordHash);

        if (!isPasswordMatched) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    private record LoginUser(Long id, String passwordHash) {
    }
}