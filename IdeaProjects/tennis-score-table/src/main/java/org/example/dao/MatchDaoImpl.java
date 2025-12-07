package org.example.dao;

import org.example.model.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchDaoImpl extends AbstractDao<Match, Integer> implements MatchDao {

    public MatchDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Match.class);
    }

    @Override
    public List<Match> findByPlayerName(String playerName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Match m WHERE m.player1.name = :name OR m.player2.name = :name", Match.class)
                    .setParameter("name", playerName)
                    .list();
        }
    }
}
