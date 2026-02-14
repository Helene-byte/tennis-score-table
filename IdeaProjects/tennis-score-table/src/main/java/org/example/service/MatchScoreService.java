package org.example.service;

import lombok.Data;
import org.example.model.MatchScore;
import org.example.model.OngoingMatch;
import org.example.dto.PlayerScoreDto;
import org.example.model.RegularGamePoints;

@Data
public class MatchScoreService {
    public MatchScore updateScore(OngoingMatch ongoingMatch, long pointWinnerId) {
        // Find which player won by ID
        int winner = ongoingMatch.getMatch().getPlayer1().getId() == pointWinnerId ? 1 : 2;
        MatchScore score = ongoingMatch.getScore();
        // Update score
        pointWon(score, winner);
        // Handle match finish, set winner, etc.
        if (score.isFinished()) {
            ongoingMatch.getMatch().setWinner(
                    winner == 1 ? ongoingMatch.getMatch().getPlayer1() : ongoingMatch.getMatch().getPlayer2()
            );
            // Optionally: persist and remove ongoing match here
        }
        // Return DTO for API
        return score;
    }

    public void pointWon(MatchScore score, int player) {
        if (score.isFinished()) return;

        if (score.isTieBreak()) {
            if (player == 1) score.setPlayer1TieBreakPoints(score.getPlayer1TieBreakPoints() + 1);
            else score.setPlayer2TieBreakPoints(score.getPlayer2TieBreakPoints() + 1);
            if (isTieBreakWon(score)) {
                if (score.getPlayer1TieBreakPoints() > score.getPlayer2TieBreakPoints())
                    score.getPlayer1().setSets(score.getPlayer1().getSets() + 1);
                else
                    score.getPlayer2().setSets(score.getPlayer2().getSets() + 1);
                resetSet(score);
                checkMatchFinished(score);
            }
            return;
        }

        PlayerScoreDto winnerScore = (player == 1) ? score.getPlayer1() : score.getPlayer2();
        PlayerScoreDto loserScore = (player == 1) ? score.getPlayer2() : score.getPlayer1();

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
                    winGame(score, player);
                }
                break;
            case ADVANTAGE:
                winGame(score, player);
                break;
        }
    }

    private void winGame(MatchScore score, int player) {
        if (player == 1) score.getPlayer1().setGames(score.getPlayer1().getGames() + 1);
        else score.getPlayer2().setGames(score.getPlayer2().getGames() + 1);

        score.getPlayer1().setPoints(RegularGamePoints.LOVE);
        score.getPlayer2().setPoints(RegularGamePoints.LOVE);

        if (isSetTieBreak(score)) {
            score.setTieBreak(true);
        } else if (isSetWon(score)) {
            if (score.getPlayer1().getGames() > score.getPlayer2().getGames())
                score.getPlayer1().setSets(score.getPlayer1().getSets() + 1);
            else

                score.getPlayer2().setSets(score.getPlayer2().getSets() + 1);
            resetSet(score);
            checkMatchFinished(score);
        }
    }

    private void resetSet(MatchScore score) {
        score.getPlayer1().setGames(0);
        score.getPlayer2().setGames(0);
        score.setPlayer1TieBreakPoints(0);
        score.setPlayer2TieBreakPoints(0);
        score.setTieBreak(false);
    }

    private boolean isSetTieBreak(MatchScore score) {
        return score.getPlayer1().getGames() == 6 && score.getPlayer2().getGames() == 6;
    }

    private boolean isSetWon(MatchScore score) {
        int p1 = score.getPlayer1().getGames();
        int p2 = score.getPlayer2().getGames();
        return (p1 >= 6 && p1 - p2 >= 2) || (p2 >= 6 && p2 - p1 >= 2);
    }

    private boolean isTieBreakWon(MatchScore score) {
        int p1 = score.getPlayer1TieBreakPoints();
        int p2 = score.getPlayer2TieBreakPoints();
        return (p1 >= 7 || p2 >= 7) && Math.abs(p1 - p2) >= 2;
    }

    private void checkMatchFinished(MatchScore score) {
        if (score.getPlayer1().getSets() == 2) {
            score.setFinished(true);
            score.setWinner(1);
        } else if (score.getPlayer2().getSets() == 2) {
            score.setFinished(true);
            score.setWinner(2);
        }
    }
}
