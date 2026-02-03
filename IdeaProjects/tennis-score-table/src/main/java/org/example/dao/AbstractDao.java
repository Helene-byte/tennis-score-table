package org.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;
public class AbstractDao <T, ID> implements Dao<T, ID> {
        protected final SessionFactory sessionFactory;
        private final Class<T> entityClass;

        protected AbstractDao(SessionFactory sessionFactory, Class<T> entityClass) {
            this.sessionFactory = sessionFactory;
            this.entityClass = entityClass;
        }

        @Override
        public T save(T entity) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.saveOrUpdate(entity);
                session.getTransaction().commit();
                return entity;
            }
        }

        @Override
        public Optional<T> findById(ID id) {
            try (Session session = sessionFactory.openSession()) {
                return Optional.ofNullable(session.get(entityClass, id));
            }
        }

        @Override
        public List<T> findAll() {
            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
            }
        }

    @Override
    public List<T> findAll(int offset, int limit) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .list();
        }
    }

    @Override
    public int countAll() {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }
}
