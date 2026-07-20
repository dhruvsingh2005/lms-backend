package com.arishi.lms_backend.service.serviceImpl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arishi.lms_backend.service.FileStorageService;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LocalFileStorageService implements FileStorageService {

	private final Path assignmentRoot;

	public LocalFileStorageService(@Value("${lms.storage.assignment-dir:./storage/assignments}") String assignmentDir) {
		this.assignmentRoot = Paths.get(assignmentDir).toAbsolutePath().normalize();
	}

	@PostConstruct
	void init() {
		try {
			Files.createDirectories(assignmentRoot);
		} catch (IOException exception) {
			throw new UncheckedIOException("Could not initialize assignment storage", exception);
		}
	}

	@Override
	public String storeAssignmentPdf(MultipartFile file, Long courseId) {
		String storageKey = "course-" + courseId + "/" + UUID.randomUUID() + ".pdf";
		Path target = resolveStorageKey(storageKey);
		
		try {
			Files.createDirectories(target.getParent());
			file.transferTo(target);
			return storageKey;
		} catch (IOException exception) {
			throw new UncheckedIOException("Could not store assignment PDF", exception);
		}
	}

	@Override
	public String storeSubmissionPdf(MultipartFile file, Long assignmentId, Long studentId) {
		String storageKey = "assignment-" + assignmentId + "/submission-" + studentId + "/" + UUID.randomUUID() + ".pdf";
		Path target = resolveStorageKey(storageKey);
		try {
			Files.createDirectories(target.getParent());
			file.transferTo(target);
			return storageKey;
		} catch (IOException exception) {
			throw new UncheckedIOException("Could not store submission PDF", exception);
		}
	}

	@Override
	public Resource loadAssignmentPdf(String storageKey) {
		Path filePath = resolveStorageKey(storageKey);

		if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
			throw new EntityNotFoundException("Assignment PDF not found");
		}

		try {
			Resource resource = new UrlResource(filePath.toUri());
			if (!resource.exists() || !resource.isReadable()) {
				throw new EntityNotFoundException("Assignment PDF not found");
			}
			return resource;
		} catch (IOException exception) {
			throw new UncheckedIOException("Could not load assignment PDF", exception);
		}
	}

	@Override
	public Resource loadSubmissionPdf(String storageKey) {
		return loadAssignmentPdf(storageKey);
	}

	private Path resolveStorageKey(String storageKey) {
		Path resolvedPath = assignmentRoot.resolve(storageKey).normalize();
		if (!resolvedPath.startsWith(assignmentRoot)) {
			throw new SecurityException("Invalid assignment storage key");
		}
		return resolvedPath;
	}
}
