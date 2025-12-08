package org.example.service;

import org.example.model.entity.Match;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private final Map<UUID, Match> ongoingMatches = new ConcurrentHashMap<>();

    public UUID createMatch(Match match) {
        UUID matchId = UUID.randomUUID();
        ongoingMatches.put(matchId, match);
        return matchId;
    }

    public Match getMatch(UUID matchId) {
        return ongoingMatches.get(matchId);
    }

    public void removeMatch(UUID matchId) {
        ongoingMatches.remove(matchId);
    }
}
