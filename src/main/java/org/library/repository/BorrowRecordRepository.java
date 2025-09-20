package org.library.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.library.entity.BorrowRecord;
import org.library.util.HibernateUtil;

import java.util.List;

public class BorrowRecordRepository {
    private static final Logger logger = LogManager.getLogger(BorrowRecordRepository.class);

    public void save(BorrowRecord record) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(record);
            transaction.commit();
            logger.info("Borrow record saved: {}", record.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Error saving borrow record", e);
        }
    }

    public BorrowRecord findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BorrowRecord.class, id);
        }
    }

    public List<BorrowRecord> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from BorrowRecord", BorrowRecord.class).list();
        }
    }
}
