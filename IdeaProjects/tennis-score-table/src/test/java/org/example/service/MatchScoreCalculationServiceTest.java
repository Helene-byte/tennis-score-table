package org.example.service;

import org.example.model.dto.MatchScoreModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchScoreCalculationServiceTest {
    @Test
    void testSimpleGameWin() {
        MatchScoreModel score = new MatchScoreModel();
        MatchScoreCalculationService service = new MatchScoreCalculationService();

        // Player 1 wins 4 points in a row
        service.pointWon(score, 1);
        service.pointWon(score, 1);
        service.pointWon(score, 1);
        service.pointWon(score, 1);

        assertEquals(1, score.getPlayer1Games());
        assertEquals(0, score.getPlayer1Points());
        assertEquals(0, score.getPlayer2Points());
    }

    @Test
    void testDeuceAdvantage() {
        MatchScoreModel score = new MatchScoreModel();
        MatchScoreCalculationService service = new MatchScoreCalculationService();

        // 3 points each (deuce)
        for (int i = 0; i < 3; i++) {
            service.pointWon(score, 1);
            service.pointWon(score, 2);
        }
        // Player 1 gets advantage
        service.pointWon(score, 1);
        // Player 2 brings back to deuce
        service.pointWon(score, 2);
        // Player 1 wins two in a row
        service.pointWon(score, 1);
        service.pointWon(score, 1);

        assertEquals(1, score.getPlayer1Games());
    }

    @Test
    void testSetWin() {
        MatchScoreModel score = new MatchScoreModel();
        MatchScoreCalculationService service = new MatchScoreCalculationService();

        // Player 1 wins 6 games
        for (int g = 0; g < 6; g++) {
            for (int p = 0; p < 4; p++) service.pointWon(score, 1);
        }
        assertEquals(1, score.getPlayer1Sets());
        assertEquals(0, score.getPlayer1Games());
    }

    @Test
    void testTieBreak() {
        MatchScoreModel score = new MatchScoreModel();
        MatchScoreCalculationService service = new MatchScoreCalculationService();

        // Both players win 6 games
        for (int g = 0; g < 6; g++) {
            for (int p = 0; p < 4; p++) service.pointWon(score, 1);
            for (int p = 0; p < 4; p++) service.pointWon(score, 2);
        }
        assertTrue(score.isTieBreak());

        // Player 1 wins tie-break 7-5
        for (int i = 0; i < 7; i++) service.pointWon(score, 1);
        for (int i = 0; i < 5; i++) service.pointWon(score, 2);

        assertEquals(1, score.getPlayer1Sets());
        assertFalse(score.isTieBreak());
    }

    @Test
    void testMatchWin() {
        MatchScoreModel score = new MatchScoreModel();
        MatchScoreCalculationService service = new MatchScoreCalculationService();

        // Player 1 wins two sets
        for (int s = 0; s < 2; s++) {
            for (int g = 0; g < 6; g++) {
                for (int p = 0; p < 4; p++) service.pointWon(score, 1);
            }
        }
        assertTrue(score.isFinished());
        assertEquals(1, score.getWinner());
    }

}