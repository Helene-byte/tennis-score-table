package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.BadRequestException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuidStr = req.getParameter("uuid");
        if (uuidStr == null || uuidStr.isEmpty()) {
            throw new BadRequestException("Missing or empty uuid parameter");
        }
        UUID matchId = UUID.fromString(uuidStr);

        OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
        if (ongoingMatch == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Match not found\"}");
            return;
        }
        MatchScoreModel score = ongoingMatch.getScore();
        new com.fasterxml.jackson.databind.ObjectMapper().writeValue(resp.getWriter(), score);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuidStr = req.getParameter("uuid");
        UUID matchId = UUID.fromString(uuidStr);
        int winner = Integer.parseInt(req.getParameter("winner"));

        OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
        if (ongoingMatch == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Match not found\"}");
            return;
        }
        MatchScoreModel score = ongoingMatch.getScore();
        scoreService.pointWon(score, winner);

        new com.fasterxml.jackson.databind.ObjectMapper().writeValue(resp.getWriter(), score);
    }
}