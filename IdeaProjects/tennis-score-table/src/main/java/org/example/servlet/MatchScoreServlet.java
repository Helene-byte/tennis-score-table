package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.model.MatchScore;
import org.example.dto.ScoreDto;
import org.example.mapper.ScoreMapper;
import org.example.service.MatchScoreService;
import org.example.model.OngoingMatch;

import org.example.service.FinishedMatchesPersistenceService;
import org.example.service.OngoingMatchesService;
import org.example.util.ValidationUtil;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebServlet("/match-score")

public class MatchScoreServlet extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;
    private FinishedMatchesPersistenceService finishedMatchesService;
    private MatchScoreService scoreService;

    @Override
    public void init() {
        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("sessionFactory");
        finishedMatchesService = new FinishedMatchesPersistenceService(sessionFactory);
        scoreService = new MatchScoreService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

            UUID matchId = ValidationUtil.getValidUuid(req.getParameter("uuid"));

            OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
            req.setAttribute("match", ongoingMatch.getMatch());
            req.setAttribute("score", ongoingMatch.getScore());
            req.setAttribute("uuid", matchId.toString());
            req.getRequestDispatcher("/WEB-INF/jsp/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");

            UUID matchId = ValidationUtil.getValidUuid(req.getParameter("uuid"));
            long pointWinnerId = ValidationUtil.getValidPointWinnerId(req.getParameter("player_id"));

            OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
            MatchScore score = scoreService.updateScore(ongoingMatch, pointWinnerId);

            if (score.isFinished()) {
                finishedMatchesService.saveFinishedMatch(ongoingMatch.getMatch());
                ongoingMatchesService.removeMatch(matchId);
            }

            ScoreDto scoreDto = ScoreMapper.convertToDto(score);
            new ObjectMapper().writeValue(resp.getWriter(), scoreDto);
    }
}