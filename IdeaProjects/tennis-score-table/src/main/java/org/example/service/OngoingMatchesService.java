package org.example.service;

import org.example.model.dto.MatchScore;
import org.example.model.dto.OngoingMatch;
import org.example.entity.Match;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private final Map<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();

    public UUID createMatch(Match match) {
        MatchScore score = new MatchScore();
        OngoingMatch ongoingMatch = new OngoingMatch(match, score);
        UUID matchId = UUID.randomUUID();
        ongoingMatches.put(matchId, ongoingMatch);
        return matchId;
    }

    public OngoingMatch getOngoingMatch(UUID matchId) {
        return ongoingMatches.get(matchId);
    }

    public void removeMatch(UUID matchId) {
        ongoingMatches.remove(matchId);
    }
}
