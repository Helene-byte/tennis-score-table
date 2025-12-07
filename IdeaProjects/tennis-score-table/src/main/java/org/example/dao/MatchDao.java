package org.example.dao;

import org.example.model.Match;

import java.util.List;

public interface MatchDao extends Dao<Match, Integer> {

    List<Match> findByPlayerName(String playerName);
}
