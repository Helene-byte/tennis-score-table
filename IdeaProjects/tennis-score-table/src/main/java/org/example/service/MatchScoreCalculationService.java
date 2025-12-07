package org.example.service;

import org.example.model.dto.MatchScoreModel;

public class MatchScoreCalculationService {
    public void pointWon(MatchScoreModel score, int player) {
        if (score.isFinished()) return;

        if (score.isTieBreak()) {
            if (player == 1) score.setPlayer1TieBreakPoints(score.getPlayer1TieBreakPoints() + 1);
            else score.setPlayer2TieBreakPoints(score.getPlayer2TieBreakPoints() + 1);

            if (isTieBreakWon(score)) {
                if (score.getPlayer1TieBreakPoints() > score.getPlayer2TieBreakPoints()) score.setPlayer1Sets(score.getPlayer1Sets() + 1);
                else score.setPlayer2Sets(score.getPlayer2Sets() + 1);
                score.setTieBreak(false);
                score.setPlayer1Games(0);
                score.setPlayer2Games(0);
                score.setPlayer1TieBreakPoints(0);
                score.setPlayer2TieBreakPoints(0);
                checkMatchFinished(score);
            }
            return;
        }

        if (player == 1) score.setPlayer1Points(score.getPlayer1Points() + 1);
        else score.setPlayer2Points(score.getPlayer2Points() + 1);

        if (isGameWon(score)) {
            if (player == 1) score.setPlayer1Games(score.getPlayer1Games() + 1);
            else score.setPlayer2Games(score.getPlayer2Games() + 1);

            score.setPlayer1Points(0);
            score.setPlayer2Points(0);

            if (isSetTieBreak(score)) {
                score.setTieBreak(true);
            } else if (isSetWon(score)) {
                if (score.getPlayer1Games() > score.getPlayer2Games()) score.setPlayer1Sets(score.getPlayer1Sets() + 1);
                else score.setPlayer2Sets(score.getPlayer2Sets() + 1);
                score.setPlayer1Games(0);
                score.setPlayer2Games(0);
                checkMatchFinished(score);
            }
        }
    }

    private boolean isGameWon(MatchScoreModel score) {
        int p1 = score.getPlayer1Points();
        int p2 = score.getPlayer2Points();
        if (p1 >= 4 && p1 - p2 >= 2) return true;
        if (p2 >= 4 && p2 - p1 >= 2) return true;
        return false;
    }

    private boolean isSetTieBreak(MatchScoreModel score) {
        return score.getPlayer1Games() == 6 && score.getPlayer2Games() == 6;
    }

    private boolean isSetWon(MatchScoreModel score) {
        int p1 = score.getPlayer1Games();
        int p2 = score.getPlayer2Games();
        if (p1 >= 6 && p1 - p2 >= 2) return true;
        if (p2 >= 6 && p2 - p1 >= 2) return true;
        return false;
    }

    private boolean isTieBreakWon(MatchScoreModel score) {
        int p1 = score.getPlayer1TieBreakPoints();
        int p2 = score.getPlayer2TieBreakPoints();
        if ((p1 >= 7 || p2 >= 7) && Math.abs(p1 - p2) >= 2) return true;
        return false;
    }

    private void checkMatchFinished(MatchScoreModel score) {
        if (score.getPlayer1Sets() == 2) {
            score.setFinished(true);
            score.setWinner(1
            );
        } else if (score.getPlayer2Sets() == 2) {
            score.setFinished(true);
            score.setWinner(2);
        }
    }
}
