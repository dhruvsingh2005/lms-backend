package com.arishi.lms_backend.service.impl;

import com.arishi.lms_backend.dto.request.LoginRequest;
import com.arishi.lms_backend.dto.response.LogInUserResponse;
import com.arishi.lms_backend.entity.Instructor;
import com.arishi.lms_backend.entity.Student;
import com.arishi.lms_backend.repo.InstructorRepo;
import com.arishi.lms_backend.repo.StudentRepo;
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

    private final StudentRepo studentRepository;
    private final InstructorRepo instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    @Override
    public LogInUserResponse login(LoginRequest request, String role, HttpServletResponse response) {

        LoginUser loginUser = getLoginUser(request.getEmail(), role);

        validatePassword(request.getPassword(), loginUser.passwordHash());

        String accessToken = authUtils.getAccessToken(loginUser.id(), role);
        
        ResponseCookie accessTokenCookie = CookieUtils.createAccessTokenCookie(accessToken);

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        
        return new LogInUserResponse(role);
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