package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.BadRequestException;
import org.example.model.dto.MatchScore;
import org.example.model.dto.OngoingMatch;
import org.example.service.FinishedMatchesPersistenceService;
import org.example.service.OngoingMatchesService;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebServlet("/match-score")

public class MatchScoreServlet extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;
    private FinishedMatchesPersistenceService finishedMatchesService;
    @Override
    public void init() {
        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("sessionFactory");
        finishedMatchesService = new FinishedMatchesPersistenceService(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuidStr = req.getParameter("uuid");
        if (uuidStr == null || uuidStr.isEmpty()) {
            throw new BadRequestException("Missing or empty uuid parameter");
        }
        UUID matchId = UUID.fromString(uuidStr);

        OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
        if (ongoingMatch == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("error", "Match not found");
            req.getRequestDispatcher("/WEB-INF/jsp/match-score.jsp").forward(req, resp);
            return;
        }
        MatchScore score = ongoingMatch.getScore();

        // Передаём данные в JSP
        req.setAttribute("score", score);
        req.setAttribute("match", ongoingMatch.getMatch());
        req.getRequestDispatcher("/WEB-INF/jsp/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuidStr = req.getParameter("uuid");
        UUID matchId = UUID.fromString(uuidStr);
        int winner = Integer.parseInt(req.getParameter("winner"));

        OngoingMatch ongoingMatch = ongoingMatchesService.getOngoingMatch(matchId);
        if (ongoingMatch == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Match not found\"}");
            return;
        }
        MatchScore score = ongoingMatch.getScore();
        score.pointWon(winner);

        if (score.isFinished()) {
            ongoingMatch.getMatch().setWinner(
                    winner == 1 ? ongoingMatch.getMatch().getPlayer1() : ongoingMatch.getMatch().getPlayer2()
            );
            String player1 = ongoingMatch.getMatch().getPlayer1().getName();
            finishedMatchesService.saveFinishedMatch(ongoingMatch.getMatch());
            ongoingMatchesService.removeMatch(matchId);

            resp.sendRedirect(req.getContextPath() + "/matches?filter_by_player_name=" + URLEncoder.encode(player1, StandardCharsets.UTF_8));
            return;
        }

        req.setAttribute("score", score);
        req.setAttribute("match", ongoingMatch.getMatch());
        req.getRequestDispatcher("/WEB-INF/jsp/match-score.jsp").forward(req, resp);
    }
}