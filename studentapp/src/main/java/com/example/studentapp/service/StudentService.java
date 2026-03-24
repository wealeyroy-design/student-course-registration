package com.example.studentapp.service;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public Student saveStudent(Student student) {
        // Check for duplicate email (excluding self on edit)
        studentRepository.findByEmail(student.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(student.getId())) {
                throw new RuntimeException("A student with email '" + student.getEmail() + "' already exists.");
            }
        });
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public long countStudents() {
        return studentRepository.count();
    }

    public List<Student> getRecentStudents() {
        List<Student> all = studentRepository.findAll();
        return all.stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .limit(5)
                .toList();
    }
}
