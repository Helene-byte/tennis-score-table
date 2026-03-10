package org.example.service;

import org.example.dao.MatchDao;
import org.example.dao.MatchDaoImpl;
import org.example.entity.Match;
import org.hibernate.SessionFactory;

import java.util.List;

public class FinishedMatchesSearchService {
    private final MatchDao matchDao;

    public FinishedMatchesSearchService(SessionFactory sessionFactory) {
        this.matchDao = new MatchDaoImpl(sessionFactory);
    }

    public List<Match> findMatches(String playerName, int offset, int limit) {
        return matchDao.findMatches(playerName, offset, limit);
    }

    public int countMatches(String playerName) {
        return matchDao.countMatches(playerName);
    }

    // Если нужно получить все матчи без пагинации (например, для админки)
    public List<Match> findAllMatches() {
        return matchDao.findAll();
    }
}
