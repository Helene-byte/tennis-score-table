package org.example.dao;

import org.example.entity.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchDaoImpl extends AbstractDao<Match, Integer> implements MatchDao {

    public MatchDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Match.class);
    }

    @Override
    public List<Match> findMatches(String playerName, int offset, int limit) {
        try (Session session = sessionFactory.openSession()) {
            if (playerName == null || playerName.isEmpty()) {
                return session.createQuery(
                                "FROM Match m", Match.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .list();
            } else {
                return session.createQuery(
                                "FROM Match m WHERE m.player1.name = :name OR m.player2.name = :name", Match.class)
                        .setParameter("name", playerName)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .list();
            }
        }
    }

    @Override
    public int countMatches(String playerName) {
        try (Session session = sessionFactory.openSession()) {
            if (playerName == null || playerName.isEmpty()) {
                Long count = session.createQuery(
                                "SELECT COUNT(m) FROM Match m", Long.class)
                        .uniqueResult();
                return count != null ? count.intValue() : 0;
            } else {
                Long count = session.createQuery(
                                "SELECT COUNT(m) FROM Match m WHERE m.player1.name = :name OR m.player2.name = :name", Long.class)
                        .setParameter("name", playerName)
                        .uniqueResult();
                return count != null ? count.intValue() : 0;
            }
        }
    }
}
