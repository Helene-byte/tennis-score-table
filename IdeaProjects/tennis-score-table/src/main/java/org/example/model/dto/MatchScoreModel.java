package org.example.model.dto;

import lombok.Data;

@Data
public class MatchScoreModel {
    private int player1Points; // Points in current game
    private int player2Points;
    private int player1Games;  // Games in current set
    private int player2Games;
    private int player1Sets;   // Sets won
    private int player2Sets;
    private boolean tieBreak;
    private int player1TieBreakPoints;
    private int player2TieBreakPoints;
    private boolean finished;
    private int winner; // 1 or 2
}
