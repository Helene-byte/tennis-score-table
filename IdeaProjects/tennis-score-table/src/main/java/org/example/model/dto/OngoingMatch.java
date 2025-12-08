package org.example.model.dto;

import lombok.Data;
import org.example.model.entity.Match;

@Data
public class OngoingMatch {
    private Match match;
    private MatchScoreModel score;

    public OngoingMatch(Match match, MatchScoreModel score) {
        this.match = match;
        this.score = score;
    }
}
