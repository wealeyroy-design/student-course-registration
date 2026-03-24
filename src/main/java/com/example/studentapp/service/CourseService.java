package com.example.studentapp.service;

import com.example.studentapp.model.Course;
import com.example.studentapp.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    public Course saveCourse(Course course) {
        // Check for duplicate course code (excluding self on edit)
        courseRepository.findByCourseCode(course.getCourseCode()).ifPresent(existing -> {
            if (!existing.getId().equals(course.getId())) {
                throw new RuntimeException("A course with code '" + course.getCourseCode() + "' already exists.");
            }
        });
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public long countCourses() {
        return courseRepository.count();
    }

    public List<Course> getRecentCourses() {
        List<Course> all = courseRepository.findAll();
        return all.stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .limit(5)
                .toList();
    }
}
