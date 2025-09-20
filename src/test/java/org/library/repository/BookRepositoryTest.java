package org.library.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.library.entity.Book;
import org.library.util.HibernateUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {

    private static BookRepository bookRepository;

    @BeforeAll
    static void setup() {
        bookRepository = new BookRepository();
    }

    @Test
    @Order(1)
    void testSaveBook() {
        Book book = new Book("1984", "George Orwell");
        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
        assertEquals("1984", books.get(0).getTitle());
    }

    @Test
    @Order(2)
    void testFindById() {
        Book book = new Book("Brave New World", "Aldous Huxley");
        bookRepository.save(book);

        Book fetched = bookRepository.findById(2L);
        assertNotNull(fetched);
        assertEquals("Brave New World", fetched.getTitle());
    }
}
