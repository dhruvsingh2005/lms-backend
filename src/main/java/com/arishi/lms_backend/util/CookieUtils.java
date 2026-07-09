package com.arishi.lms_backend.util;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtils {

    private CookieUtils() {}

    public static ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from("accessToken", accessToken).httpOnly(true).secure(false).sameSite("Strict").path("/").maxAge(Duration.ofMinutes(10)).build();
    }
}