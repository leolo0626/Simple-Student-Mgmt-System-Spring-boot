package com.example.demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    @GetMapping
    public List<Student> getAllStudents() {
        List<Student> students = Arrays.asList(
                new Student(1L,
                        "Leo",
                        "leolo0626@gmail.com",
                        Gender.MALE),
                new Student(2L,
                        "Meko",
                        "meko0626@gmail.com",
                        Gender.FEMALE)
        );
        return students;
    }
}
