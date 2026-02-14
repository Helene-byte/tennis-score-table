package org.example.service;

import org.example.entity.Match;
import org.example.model.dto.MatchScore;
import org.example.model.dto.OngoingMatch;

import java.util.UUID;

public class MatchScoreService {
    private final OngoingMatchesService ongoingMatchesService;
    private final FinishedMatchesPersistenceService finishedMatchesService;

    public MatchScoreService(OngoingMatchesService ongoingMatchesService, FinishedMatchesPersistenceService finishedMatchesService) {
        this.ongoingMatchesService = ongoingMatchesService;
        this.finishedMatchesService = finishedMatchesService;
    }

    public OngoingMatch processPoint(UUID matchId, int winner) {
        OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
        if (ongoingMatch == null) {
            throw new IllegalArgumentException("Match not found");
        }
        MatchScore score = ongoingMatch.getScore();
        score.pointWon(winner);

        if (score.isFinished()) {
            Match match = ongoingMatch.getMatch();
            match.setWinner(winner == 1 ? match.getPlayer1() : match.getPlayer2());
            finishedMatchesService.saveFinishedMatch(match);
            ongoingMatchesService.removeMatch(matchId);
        }
        return ongoingMatch;
    }
}
