package com.arishi.lms_backend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	String storeAssignmentPdf(MultipartFile file, Long courseId);

	Resource loadAssignmentPdf(String storageKey);

	String storeSubmissionPdf(MultipartFile file, Long assignmentId, Long studentId);

	Resource loadSubmissionPdf(String storageKey);
}
