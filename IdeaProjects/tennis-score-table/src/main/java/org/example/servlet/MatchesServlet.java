package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Match;
import org.example.service.FinishedMatchesSearchService;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private FinishedMatchesSearchService searchService;

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("sessionFactory");
        this.searchService = new FinishedMatchesSearchService(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String playerName = req.getParameter("filter_by_player_name");
        List<Match> matches;
        if (playerName != null && !playerName.trim().isEmpty()) {
            matches = searchService.findMatchesByPlayerName(playerName.trim());
        } else {
            matches = searchService.findAllMatches();
        }

        req.setAttribute("matches", matches);
        req.getRequestDispatcher("/WEB-INF/jsp/matches.jsp").forward(req, resp);
    }
}