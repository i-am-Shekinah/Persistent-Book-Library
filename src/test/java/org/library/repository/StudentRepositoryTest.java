package org.library.repository;

import org.junit.jupiter.api.*;
import org.library.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentRepositoryTest {

    private static StudentRepository studentRepository;

    @BeforeAll
    static void setup() {
        studentRepository = new StudentRepository();
    }

    @Test
    @Order(1)
    void testSaveStudent() {
        Student student = new Student("John", "Doe");
        studentRepository.save(student);

        List<Student> students = studentRepository.findAll();
        assertEquals(1, students.size());
        assertEquals("John", students.get(0).getFirstName());
    }

    @Test
    @Order(2)
    void testFindById() {
        Student student = new Student("Jane", "Smith");
        studentRepository.save(student);

        Student fetched = studentRepository.findById(2L);
        assertNotNull(fetched);
        assertEquals("Jane", fetched.getFirstName());
    }
}
