package org.example.dao;

import org.example.entity.Match;

import java.util.List;

public interface MatchDao extends Dao<Match, Integer> {

    List<Match> findMatches(String playerName, int offset, int limit);
    int countMatches(String playerName);

}
