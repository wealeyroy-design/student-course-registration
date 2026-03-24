package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.service.CourseService;
import com.example.studentapp.service.RegistrationService;
import com.example.studentapp.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final StudentService studentService;
    private final CourseService courseService;

    public RegistrationController(RegistrationService registrationService,
                                  StudentService studentService,
                                  CourseService courseService) {
        this.registrationService = registrationService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("semesters", new String[]{
            "Semester 1 2024", "Semester 2 2024",
            "Semester 1 2025", "Semester 2 2025",
            "Semester 1 2026", "Semester 2 2026"
        });
        return "registrations/register";
    }

    @PostMapping("/register")
    public String registerStudent(@RequestParam Long studentId,
                                  @RequestParam Long courseId,
                                  @RequestParam String semester,
                                  RedirectAttributes redirectAttributes) {
        try {
            var student = studentService.getStudentById(studentId);
            var course = courseService.getCourseById(courseId);
            registrationService.register(student, course, semester);
            redirectAttributes.addFlashAttribute("successMessage",
                    student.getFullName() + " successfully registered for " + course.getCourseName() + "!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/registrations/register";
    }

    @GetMapping("/student/{studentId}")
    public String viewStudentCourses(@PathVariable Long studentId, Model model,
                                     RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.getStudentById(studentId);
            model.addAttribute("student", student);
            model.addAttribute("registrations", registrationService.getRegistrationsByStudent(student));
            return "registrations/student-courses";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/students";
        }
    }

    @PostMapping("/{id}/drop")
    public String dropCourse(@PathVariable Long id,
                             @RequestParam(required = false) Long studentId,
                             RedirectAttributes redirectAttributes) {
        try {
            registrationService.dropRegistration(id);
            redirectAttributes.addFlashAttribute("successMessage", "Course dropped successfully.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        if (studentId != null) {
            return "redirect:/registrations/student/" + studentId;
        }
        return "redirect:/registrations/register";
    }
}
