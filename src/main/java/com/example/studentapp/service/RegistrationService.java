package com.example.studentapp.service;

import com.example.studentapp.model.Course;
import com.example.studentapp.model.Registration;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public List<Registration> getRegistrationsByStudent(Student student) {
        return registrationRepository.findByStudent(student);
    }

    public Registration register(Student student, Course course, String semester) {
        if (registrationRepository.existsByStudentAndCourseAndSemester(student, course, semester)) {
            throw new RuntimeException(
                student.getFullName() + " is already registered for " +
                course.getCourseName() + " in " + semester + "."
            );
        }
        return registrationRepository.save(new Registration(student, course, semester));
    }

    public void dropRegistration(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }

    public long countRegistrations() {
        return registrationRepository.count();
    }
}
