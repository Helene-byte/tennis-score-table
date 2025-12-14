package org.example.model.dto;

import lombok.Data;

@Data
public class MatchScore {
    private PlayerScore player1 = new PlayerScore();
    private PlayerScore player2 = new PlayerScore();
    private boolean tieBreak = false;
    private int player1TieBreakPoints = 0;
    private int player2TieBreakPoints = 0;
    private boolean finished = false;
    private int winner = 0; // 1 or 2

    // Business logic below
    public void pointWon(int player) {
        if (finished) return;

        if (tieBreak) {
            if (player == 1) player1TieBreakPoints++;
            else player2TieBreakPoints++;
            if (isTieBreakWon()) {
                if (player1TieBreakPoints > player2TieBreakPoints) player1.setSets(player1.getSets() + 1);
                else player2.setSets(player2.getSets() + 1);
                resetSet();
                checkMatchFinished();
            }
            return;
        }

        PlayerScore winnerScore = (player == 1) ? player1 : player2;
        PlayerScore loserScore = (player == 1) ? player2 : player1;

        switch (winnerScore.getPoints()) {
            case LOVE: winnerScore.setPoints(RegularGamePoints.FIFTEEN); break;
            case FIFTEEN: winnerScore.setPoints(RegularGamePoints.THIRTY); break;
            case THIRTY: winnerScore.setPoints(RegularGamePoints.FORTY); break;
            case FORTY:
                if (loserScore.getPoints() == RegularGamePoints.FORTY) {
                    winnerScore.setPoints(RegularGamePoints.ADVANTAGE);
                } else if (loserScore.getPoints() == RegularGamePoints.ADVANTAGE) {
                    loserScore.setPoints(RegularGamePoints.FORTY);
                } else {
                    winGame(player);
                }
                break;
            case ADVANTAGE:
                winGame(player);
                break;
        }
    }

    private void winGame(int player) {
        if (player == 1) player1.setGames(player1.getGames() + 1);
        else player2.setGames(player2.getGames() + 1);

        player1.setPoints(RegularGamePoints.LOVE);
        player2.setPoints(RegularGamePoints.LOVE);

        if (isSetTieBreak()) {
            tieBreak = true;
        } else if (isSetWon()) {
            if (player1.getGames() > player2.getGames()) player1.setSets(player1.getSets() + 1);
            else player2.setSets(player2.getSets() + 1);
            resetSet();
            checkMatchFinished();
        }
    }

    private void resetSet() {
        player1.setGames(0);
        player2.setGames(0);
        player1TieBreakPoints = 0;
        player2TieBreakPoints = 0;
        tieBreak = false;
    }

    private boolean isSetTieBreak() {
        return player1.getGames() == 6 && player2.getGames() == 6;
    }

    private boolean isSetWon() {
        int p1 = player1.getGames();
        int p2 = player2.getGames();
        return (p1 >= 6 && p1 - p2 >= 2) || (p2 >= 6 && p2 - p1 >= 2);
    }

    private boolean isTieBreakWon() {
        int p1 = player1TieBreakPoints;
        int p2 = player2TieBreakPoints;
        return (p1 >= 7 || p2 >= 7) && Math.abs(p1 - p2) >= 2;
    }

    private void checkMatchFinished() {
        if (player1.getSets() == 2) {
            finished = true;
            winner = 1;
        } else if (player2.getSets() == 2) {
            finished = true;
            winner = 2;
        }
    }
}
