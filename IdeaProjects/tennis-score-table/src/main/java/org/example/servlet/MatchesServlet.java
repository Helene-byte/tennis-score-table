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
    public void init() {
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("sessionFactory");
        this.searchService = new FinishedMatchesSearchService(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String playerName = req.getParameter("filter_by_player_name");
        String pageParam = req.getParameter("page");

        int page = 1;
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        if (page < 1) page = 1;

        int pageSize = 3; // Количество матчей на страницу
        int offset = (page - 1) * pageSize;

        // Получаем матчи и их количество с учётом фильтра
        List<Match> matches = searchService.findMatches(
                (playerName != null && !playerName.trim().isEmpty()) ? playerName.trim() : null,
                offset,
                pageSize
        );
        int totalMatches = searchService.countMatches(
                (playerName != null && !playerName.trim().isEmpty()) ? playerName.trim() : null
        );
        int totalPages = (int) Math.ceil((double) totalMatches / pageSize);
        System.out.println("MatchesServlet: doGet called");
        System.out.println("Request URI: " + req.getRequestURI());
        System.out.println("Context Path: " + req.getContextPath());
        System.out.println("Filter param: " + playerName);
        System.out.println("Page param: " + page);
        System.out.println("Total matches: " + totalMatches);
        System.out.println("Total pages: " + totalPages);
        req.setAttribute("matches", matches);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("filter", playerName != null ? playerName.trim() : "");

        req.getRequestDispatcher("/WEB-INF/jsp/matches.jsp").forward(req, resp);
    }
}