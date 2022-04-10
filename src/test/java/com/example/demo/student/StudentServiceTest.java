package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }


    @Test
    void canGetAllStudents() {
        underTest.getAllStudents();

        verify(studentRepository).findAll();

    }

    @Test
    void canAddStudent() {
        // Given
        String email = "leolo0626@gmail.com";
        Student leo = new Student(
                "Leo",
                email,
                Gender.MALE
        );
        // When
        underTest.addStudent(leo);
        // Then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        Assertions.assertThat(capturedStudent).isEqualTo(leo);
    }

    @Test
    void willThrowErrorIfStudentExist() {
        // Given
        String email = "leolo0626@gmail.com";
        Student leo = new Student(
                "Leo",
                email,
                Gender.MALE
        );
        given(studentRepository.selectExistsEmail(anyString())).willReturn(true);
        // When
        // then
        Assertions.assertThatThrownBy(()->underTest.addStudent(leo))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining( "Email" + email + " taken");

        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteStudent() {
        // given
        long id = 10;
        given(studentRepository.existsById(id))
                .willReturn(true);
        // when
        underTest.deleteStudent(id);

        // then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteStudentNotFound() {
        // given
        long id = 10;
        given(studentRepository.existsById(id))
                .willReturn(false);
        // when
        // then
        Assertions.assertThatThrownBy(() -> underTest.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id" + id + " does not exists");

        verify(studentRepository, never()).deleteById(any());
    }
}