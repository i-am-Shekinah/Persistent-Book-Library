package org.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.library.entity.Book;
import org.library.entity.Student;
import org.library.repository.BookRepository;
import org.library.repository.StudentRepository;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        // Initialize repositories
        BookRepository bookRepo = new BookRepository();
        StudentRepository studentRepo = new StudentRepository();

        // Create and save a book
        Book book = new Book("The Hobbit", "J.R.R. Tolkien");
        bookRepo.save(book);

        // Create and save a student
        Student student = new Student("Alice", "Johnson");
        studentRepo.save(student);

        // Fetch and log results
        logger.info("All books: {}", bookRepo.findAll());
        logger.info("All students: {}", studentRepo.findAll());

    }
}