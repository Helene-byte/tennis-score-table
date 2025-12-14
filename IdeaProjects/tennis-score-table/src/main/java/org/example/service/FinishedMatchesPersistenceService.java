package org.example.service;

import org.example.dao.MatchDao;
import org.example.dao.MatchDaoImpl;
import org.example.entity.Match;
import org.hibernate.SessionFactory;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao;

    public FinishedMatchesPersistenceService(SessionFactory sessionFactory) {
        this.matchDao = new MatchDaoImpl(sessionFactory);
    }

    public void saveFinishedMatch(Match match) {
        matchDao.save(match);
    }
}
