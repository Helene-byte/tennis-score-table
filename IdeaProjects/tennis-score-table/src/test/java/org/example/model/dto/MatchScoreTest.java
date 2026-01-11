package org.example.model.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MatchScoreTest {
    private MatchScore score;

    @BeforeEach
    void setUp() {
        score = new MatchScore();
    }

    @Test
    void testDeuceNoGameWin() {
        // 40-40, игрок 1 выигрывает очко — становится Advantage, но гейм не заканчивается
        setPoints(RegularGamePoints.FORTY, RegularGamePoints.FORTY);
        score.pointWon(1);
        assertEquals(RegularGamePoints.ADVANTAGE, score.getPlayer1().getPoints());
        assertEquals(RegularGamePoints.FORTY, score.getPlayer2().getPoints());
        assertEquals(0, score.getPlayer1().getGames());
    }

    @Test
    void testWinGameFromFortyZero() {
        // 40-0, игрок 1 выигрывает очко — выигрывает гейм
        setPoints(RegularGamePoints.FORTY, RegularGamePoints.LOVE);
        score.pointWon(1);
        assertEquals(1, score.getPlayer1().getGames());
        assertEquals(RegularGamePoints.LOVE, score.getPlayer1().getPoints());
        assertEquals(RegularGamePoints.LOVE, score.getPlayer2().getPoints());
    }

    @Test
    void testAdvantageBackToDeuce() {
        // 40-Advantage, игрок 1 выигрывает очко — гейм, иначе возвращается к 40-40
        setPoints(RegularGamePoints.FORTY, RegularGamePoints.ADVANTAGE);
        score.pointWon(1);
        assertEquals(RegularGamePoints.FORTY, score.getPlayer1().getPoints());
        assertEquals(RegularGamePoints.FORTY, score.getPlayer2().getPoints());
    }

    @Test
    void testWinGameFromAdvantage() {
        // Advantage, игрок выигрывает очко — выигрывает гейм
        setPoints(RegularGamePoints.ADVANTAGE, RegularGamePoints.FORTY);
        score.pointWon(1);
        assertEquals(1, score.getPlayer1().getGames());
        assertEquals(RegularGamePoints.LOVE, score.getPlayer1().getPoints());
        assertEquals(RegularGamePoints.LOVE, score.getPlayer2().getPoints());
    }

    @Test
    void testTieBreakStartsAt6_6() {
        // Сначала оба игрока по 5 геймов
        score.getPlayer1().setGames(5);
        score.getPlayer2().setGames(5);

        // Игрок 1 выигрывает гейм — 6-5
        setPoints(RegularGamePoints.FORTY, RegularGamePoints.LOVE);
        score.pointWon(1);
        assertEquals(6, score.getPlayer1().getGames());
        assertEquals(5, score.getPlayer2().getGames());
        assertFalse(score.isTieBreak());

        // Игрок 2 выигрывает гейм — 6-6
        setPoints(RegularGamePoints.LOVE, RegularGamePoints.FORTY);
        score.pointWon(2);
        assertEquals(6, score.getPlayer1().getGames());
        assertEquals(6, score.getPlayer2().getGames());
        assertTrue(score.isTieBreak());
    }

    @Test
    void testTieBreakWin() {
        // Тайбрейк: игрок 1 выигрывает 7-5
        score.setTieBreak(true);
        score.setPlayer1TieBreakPoints(6);
        score.setPlayer2TieBreakPoints(5);
        score.pointWon(1); // 7-5, игрок 1 выигрывает сет
        assertEquals(1, score.getPlayer1().getSets());
        assertFalse(score.isTieBreak());
        assertEquals(0, score.getPlayer1().getGames());
        assertEquals(0, score.getPlayer2().getGames());
    }

    @Test
    void testMatchFinish() {
        // Игрок 1 выигрывает 2 сета — матч завершён
        score.getPlayer1().setSets(1);
        // Выигрываем ещё один сет через winGame
        score.getPlayer1().setGames(5);
        score.getPlayer2().setGames(3);
        setPoints(RegularGamePoints.FORTY, RegularGamePoints.LOVE);
        score.pointWon(1); // игрок 1 выигрывает гейм, а значит и сет, а значит и матч
        assertTrue(score.isFinished());
        assertEquals(1, score.getWinner());
    }

    // Вспомогательный метод для установки очков
    private void setPoints(RegularGamePoints p1, RegularGamePoints p2) {
        score.getPlayer1().setPoints(p1);
        score.getPlayer2().setPoints(p2);
    }
}