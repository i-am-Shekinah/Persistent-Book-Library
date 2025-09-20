package org.library.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // A student can borrow multiple books
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;


    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;


    @Column(nullable = false)
    private boolean returned;


    @Column(nullable = false)
    private LocalDateTime borrowedAt =  LocalDateTime.now();


    private LocalDateTime returnedAt =  LocalDateTime.now();


    public BorrowRecord() {}

    public BorrowRecord(Student student, Book book) {
        this.student = student;
        this.book = book;
        this.returned = false;
        this.borrowedAt = LocalDateTime.now();
    }


    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public boolean isReturned() {
        return returned;
    }
    public void setReturned(boolean returned) {
        this.returned = returned;
        if (returned) {
            this.returnedAt = LocalDateTime.now();
        }
    }
    public  LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }
    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "id=" + id +
                ", student=" + student +
                ", book=" + book +
                ", returned=" + returned +
                ", borrowedAt=" + borrowedAt +
                ", returnedAt=" + returnedAt +
                '}';
    }
}
