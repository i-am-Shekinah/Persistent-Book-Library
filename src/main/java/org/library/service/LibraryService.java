package org.library.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.library.entity.Book;
import org.library.entity.BorrowRecord;
import org.library.entity.Student;
import org.library.repository.BookRepository;
import org.library.repository.BorrowRecordRepository;
import org.library.repository.StudentRepository;

import java.util.List;

public class LibraryService {
    private static final Logger logger = LogManager.getLogger(LibraryService.class);

    private final StudentRepository studentRepo;
    private final BookRepository bookRepo;
    private final BorrowRecordRepository borrowRepo;

    public LibraryService(StudentRepository studentRepo,
                          BookRepository bookRepo,
                          BorrowRecordRepository borrowRepo) {
        this.studentRepo = studentRepo;
        this.bookRepo = bookRepo;
        this.borrowRepo = borrowRepo;
    }

    // ------------------------------
    // Create student
    // ------------------------------
    public void createStudent(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
        studentRepo.save(student);
        logger.info("Student created: {} {}", firstName, lastName);
    }

    // ------------------------------
    // Create book
    // ------------------------------
    public void createBook(String title, String author) {
        Book book = new Book(title, author);
        bookRepo.save(book);
        logger.info("Book created: {} by {}", title, author);
    }

    // ------------------------------
    // Borrow book
    // ------------------------------
    public String borrowBook(Long studentId, Long bookId) {
        Student student = studentRepo.findById(studentId);
        if (student == null) {
            return "Student not found.";
        }

        Book book = bookRepo.findById(bookId);
        if (book == null) {
            return "Book not found.";
        }

        // Check if this student already has a borrow record
        List<BorrowRecord> allRecords = borrowRepo.findAll();
        boolean alreadyBorrowed = allRecords.stream()
                .anyMatch(r -> r.getStudent().getId().equals(studentId));

        if (alreadyBorrowed) {
            logger.warn("Student {} {} tried to borrow a second book without returning the first.",
                    student.getFirstName(), student.getLastName());
            return "You must return your previously borrowed book before borrowing another.";
        }

        BorrowRecord record = new BorrowRecord(student, book);
        borrowRepo.save(record);
        logger.info("Book '{}' borrowed by {} {}", book.getTitle(), student.getFirstName(), student.getLastName());
        return "Book borrowed successfully.";
    }

    // ------------------------------
    // Return book
    // ------------------------------
    public String returnBook(Long studentId) {
        List<BorrowRecord> allRecords = borrowRepo.findAll();
        BorrowRecord record = allRecords.stream()
                .filter(r -> r.getStudent().getId().equals(studentId))
                .findFirst()
                .orElse(null);

        if (record == null) {
            return "No borrowed book found for this student.";
        }

        // Delete the borrow record
        // We'll just remove by re-saving list except this, but since we have repo.save only,
        // we should extend repo to support delete
        try {
            borrowRepo.delete(record.getId());
            logger.info("Student {} {} returned book '{}'",
                    record.getStudent().getFirstName(),
                    record.getStudent().getLastName(),
                    record.getBook().getTitle());
            return "Book returned successfully.";
        } catch (Exception e) {
            logger.error("Error returning book", e);
            return "Error occurred while returning the book.";
        }
    }
}
