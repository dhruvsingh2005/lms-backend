package com.arishi.lms_backend.service;

import com.arishi.lms_backend.dto.InstructorDTO;

public interface InstructorService {

    InstructorDTO createInstructor(InstructorDTO request);

    InstructorDTO getInstructorProfile();
}
