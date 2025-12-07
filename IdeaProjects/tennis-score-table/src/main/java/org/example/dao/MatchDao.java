package org.example.dao;

import org.example.model.entity.Match;

import java.util.List;

public interface MatchDao extends Dao<Match, Integer> {

    List<Match> findByPlayerName(String playerName);
}
