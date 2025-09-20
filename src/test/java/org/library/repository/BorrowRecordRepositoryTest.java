package org.library.repository;

import org.junit.jupiter.api.*;
import org.library.entity.Book;
import org.library.entity.BorrowRecord;
import org.library.entity.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BorrowRecordRepositoryTest {

    private static BorrowRecordRepository borrowRepo;
    private static BookRepository bookRepo;
    private static StudentRepository studentRepo;

    @BeforeAll
    static void setup() {
        borrowRepo = new BorrowRecordRepository();
        bookRepo = new BookRepository();
        studentRepo = new StudentRepository();
    }

    @Test
    @Order(1)
    void testSaveBorrowRecord() {
        // Create book + student
        Book book = new Book("Dune", "Frank Herbert");
        bookRepo.save(book);
        Student student = new Student("Paul", "Atreides");
        studentRepo.save(student);

        // Create borrow record
        BorrowRecord record = new BorrowRecord(student, book);
        borrowRepo.save(record);

        List<BorrowRecord> records = borrowRepo.findAll();
        assertEquals(1, records.size());
        assertEquals("Paul", records.get(0).getStudent().getFirstName());
        assertEquals("Dune", records.get(0).getBook().getTitle());
    }

    @Test
    @Order(2)
    void testFindById() {
        // Create another borrow record
        Book book = new Book("Foundation", "Isaac Asimov");
        bookRepo.save(book);
        Student student = new Student("Hari", "Seldon");
        studentRepo.save(student);

        BorrowRecord record = new BorrowRecord(student, book);
        borrowRepo.save(record);

        BorrowRecord fetched = borrowRepo.findById(2L);
        assertNotNull(fetched);
        assertEquals("Foundation", fetched.getBook().getTitle());
    }
}
