package com.example.demo.student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll(); // after each test, we delete everyone in h2 database
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {
        String email = "leolo0626@gmail.com";
        Student leo = new Student(
                "Leo",
                email,
                Gender.MALE
        );
        underTest.save(leo);

        boolean expected = underTest.selectExistsEmail(email);

        Assertions.assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenStudentEmailNotExists() {
        String email = "leolo0626@gmail.com";

        boolean expected = underTest.selectExistsEmail(email);

        Assertions.assertThat(expected).isFalse();
    }
}