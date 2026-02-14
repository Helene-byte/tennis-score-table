package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.PlayerDao;
import org.example.dao.PlayerDaoImpl;

import org.example.service.MatchCreationService;
import org.example.service.OngoingMatchesService;
import org.hibernate.SessionFactory;

import java.io.IOException;

import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private MatchCreationService matchCreationService;

    @Override
    public void init() {
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("sessionFactory");
        OngoingMatchesService ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        PlayerDao playerDao = new PlayerDaoImpl(sessionFactory);
        matchCreationService = new MatchCreationService(playerDao, ongoingMatchesService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String playerOneName = req.getParameter("playerOneName");
        String playerTwoName = req.getParameter("playerTwoName");

        try {
            UUID matchId = matchCreationService.createNewMatch(playerOneName, playerTwoName);
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchId);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/new-match.jsp").forward(req, resp);
        }
    }
}