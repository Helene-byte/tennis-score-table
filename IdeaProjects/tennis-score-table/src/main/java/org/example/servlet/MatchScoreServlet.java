package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.dto.MatchScoreModel;
import org.example.model.dto.OngoingMatch;
import org.example.service.MatchScoreCalculationService;
import org.example.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
        private OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        private MatchScoreCalculationService scoreService = new MatchScoreCalculationService();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String uuidStr = req.getParameter("uuid");
            UUID matchId = UUID.fromString(uuidStr);

            // Получить матч и счет
            OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
            if (ongoingMatch == null) {
                // handle error: match not found
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
                return;
            }
            MatchScoreModel score = ongoingMatch.getScore();
            String player1Name = ongoingMatch.getMatch().getPlayer1().getName();
            String player2Name = ongoingMatch.getMatch().getPlayer2().getName();

            req.setAttribute("score", score);
            req.setAttribute("player1Name", player1Name);
            req.setAttribute("player2Name", player2Name);
            req.setAttribute("matchId", matchId);

            req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String uuidStr = req.getParameter("uuid");
            UUID matchId = UUID.fromString(uuidStr);
            int
                    winner = Integer.parseInt(req.getParameter("winner"));

            OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
            if (ongoingMatch == null) {
                // handle error: match not found
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
                return;
            }
            MatchScoreModel score = ongoingMatch.getScore();
            scoreService.pointWon(score, winner);

            // Если матч завершён, можно добавить логику сохранения в БД и удаления из ongoingMatchesService

            resp.sendRedirect("match-score?uuid=" + matchId);
        }
}
