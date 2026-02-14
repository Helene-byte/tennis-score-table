package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    private PlayerScoreDto player1 = new PlayerScoreDto();
    private PlayerScoreDto player2 = new PlayerScoreDto();
    private boolean tieBreak = false;
    private int player1TieBreakPoints = 0;
    private int player2TieBreakPoints = 0;
    private boolean finished = false;
    private int winner = 0; // 1 or 2
}
