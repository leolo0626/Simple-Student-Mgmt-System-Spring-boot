package com.example.demo.integration;

import com.example.demo.student.Gender;
import com.example.demo.student.Student;
import com.example.demo.student.StudentController;
import com.example.demo.student.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class StudentIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    private final Faker faker = new Faker();

    @Test
    void canRegisterNewStudent() throws Exception{
        //given

        String name =   String.format("%s %s",
                faker.name().firstName(),
                faker.name().lastName());
        Student leo = new Student(
                name,
                String.format("%s@leo.edu.com",   StringUtils.trimAllWhitespace(name.trim().toLowerCase())),
                Gender.MALE
        );
        //when
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/students").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(leo)));
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        List<Student> students = studentRepository.findAll();
        Assertions.assertThat(students)
                .usingElementComparatorIgnoringFields("id")
                .contains(leo);

    }
}
