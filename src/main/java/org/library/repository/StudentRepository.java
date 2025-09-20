package org.library.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.library.entity.Student;
import org.library.util.HibernateUtil;

import java.util.List;

public class StudentRepository {
    private static final Logger logger = LogManager.getLogger(StudentRepository.class);

    public void save(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
            logger.info("Student saved: {}", student.getFirstName() + " " + student.getLastName());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error saving student", e);
        }
    }

    public Student findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, id);
        }
    }

    public List<Student> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        }
    }
}
