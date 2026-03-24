package com.example.studentapp.repository;

import com.example.studentapp.model.Course;
import com.example.studentapp.model.Registration;
import com.example.studentapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByStudent(Student student);
    List<Registration> findByCourse(Course course);
    Optional<Registration> findByStudentAndCourseAndSemester(Student student, Course course, String semester);
    boolean existsByStudentAndCourseAndSemester(Student student, Course course, String semester);
}
