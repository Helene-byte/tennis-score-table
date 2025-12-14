package org.example.model.dto;

import lombok.Data;
import org.example.entity.Match;

@Data
public class OngoingMatch {
    private Match match;
    private MatchScore score;

    public OngoingMatch(Match match, MatchScore score) {
        this.match = match;
        this.score = score;
    }
}
