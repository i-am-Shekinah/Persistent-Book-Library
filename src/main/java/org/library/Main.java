package org.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.library.entity.Book;
import org.library.entity.Student;
import org.library.repository.BookRepository;
import org.library.repository.BorrowRecordRepository;
import org.library.repository.StudentRepository;
import org.library.service.LibraryService;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        // Instantiate repositories
        StudentRepository studentRepo = new StudentRepository();
        BookRepository bookRepo = new BookRepository();
        BorrowRecordRepository borrowRepo = new BorrowRecordRepository();

        // Service
        LibraryService libraryService = new LibraryService(studentRepo, bookRepo, borrowRepo);

        // Create student
        Student student = new Student();
        student.setFirstName("Michael");
        student.setLastName("Olatunji");
        studentRepo.save(student);
        logger.info("Created student: {} {}", student.getFirstName(), student.getLastName());

        // Create book
        Book book = new Book();
        book.setTitle("Hibernate Basics");
        book.setAuthor("Tutorials Point");
        bookRepo.save(book);
        logger.info("Created book: {}", book.getTitle());

        // Borrow book
        libraryService.borrowBook(student.getId(), book.getId());

        // Try borrowing again (should fail)
        libraryService.borrowBook(student.getId(), book.getId());

        // Return book
        libraryService.returnBook(student.getId());

        // Borrow again (should succeed now)
        libraryService.borrowBook(student.getId(), book.getId());


    }
}
