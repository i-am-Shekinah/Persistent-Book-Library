package org.library.service;

import org.junit.jupiter.api.*;
import org.library.entity.Book;
import org.library.entity.Student;
import org.library.repository.BookRepository;
import org.library.repository.BorrowRecordRepository;
import org.library.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LibraryServiceTest {

    private static LibraryService libraryService;
    private static StudentRepository studentRepo;
    private static BookRepository bookRepo;
    private static BorrowRecordRepository borrowRepo;

    private static Long studentId;
    private static Long bookId;

    @BeforeAll
    static void setup() {
        studentRepo = new StudentRepository();
        bookRepo = new BookRepository();
        borrowRepo = new BorrowRecordRepository();
        libraryService = new LibraryService(studentRepo, bookRepo, borrowRepo);
    }

    @Test
    @Order(1)
    void testCreateStudentAndBook() {
        libraryService.createStudent("Luke", "Skywalker");
        libraryService.createBook("Star Wars", "George Lucas");

        List<Student> students = studentRepo.findAll();
        List<Book> books = bookRepo.findAll();

        assertEquals(1, students.size());
        assertEquals("Luke", students.get(0).getFirstName());

        assertEquals(1, books.size());
        assertEquals("Star Wars", books.get(0).getTitle());

        studentId = students.get(0).getId();
        bookId = books.get(0).getId();
    }

    @Test
    @Order(2)
    void testBorrowBookSuccessfully() {
        String result = libraryService.borrowBook(studentId, bookId);
        assertEquals("Book borrowed successfully.", result);
    }

    @Test
    @Order(3)
    void testBorrowBookFailsIfAlreadyBorrowed() {
        String result = libraryService.borrowBook(studentId, bookId);
        assertEquals("You must return your previously borrowed book before borrowing another.", result);
    }

    @Test
    @Order(4)
    void testReturnBookSuccessfully() {
        String result = libraryService.returnBook(studentId);
        assertEquals("Book returned successfully.", result);
    }

    @Test
    @Order(5)
    void testReturnBookFailsIfNoRecord() {
        String result = libraryService.returnBook(studentId);
        assertEquals("No borrowed book found for this student.", result);
    }
}
