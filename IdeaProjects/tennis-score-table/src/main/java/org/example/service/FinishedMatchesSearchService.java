package org.example.service;

import org.example.dao.MatchDao;
import org.example.dao.MatchDaoImpl;
import org.example.model.entity.Match;
import org.hibernate.SessionFactory;

import java.util.List;

public class FinishedMatchesSearchService {
    private final MatchDao matchDao;

    public FinishedMatchesSearchService(SessionFactory sessionFactory) {
        this.matchDao = new MatchDaoImpl(sessionFactory);
    }

    public List<Match> findMatchesByPlayerName(String playerName) {
        return matchDao.findByPlayerName(playerName);
    }

    public List<Match> findAllMatches() {
        return matchDao.findAll();
    }
}
