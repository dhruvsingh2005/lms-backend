package com.arishi.lms_backend.validator;

import org.springframework.web.multipart.MultipartFile;
import com.arishi.lms_backend.exception.customException.InvalidPdfException;
import java.util.List;

public class PdfValidator {
	
	public static void validatePDF(MultipartFile file, List<String> errors) {
        // Check if file is empty
        if (file.isEmpty()) {
            throw new InvalidPdfException("Assignment File is empty");
        }
        
        // Check file size (10MB max)
        long maxSize = 10 * 1024 * 1024; // 10MB in bytes
        if (file.getSize() > maxSize) {
            throw new InvalidPdfException("Assignment file size must not exceed 10MB");
        }
        
        // Check content type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
        	throw new InvalidPdfException("For assignment, Only PDF files are allowed");
        }
        
        // Check original filename extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".pdf")) {
        	throw new InvalidPdfException("Assignment file must have .pdf extension");
        }
        
        // Check for empty filename (security check)
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
        	throw new InvalidPdfException("Invalid assignment file name");
        }
        
        // Check for path traversal in filename (security)
        if (originalFilename != null && 
            (originalFilename.contains("..") || 
             originalFilename.contains("/") || 
             originalFilename.contains("\\"))) {
        	throw new InvalidPdfException("Assignment file name contains invalid characters");
        }
    }
}