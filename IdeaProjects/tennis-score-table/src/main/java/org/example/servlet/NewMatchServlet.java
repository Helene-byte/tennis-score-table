package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.PlayerDao;
import org.example.dao.PlayerDaoImpl;
import org.example.entity.Match;
import org.example.entity.Player;
import org.example.service.OngoingMatchesService;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private PlayerDao playerDao;
    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init() {
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("sessionFactory");
        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        playerDao = new PlayerDaoImpl(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String playerOneName = req.getParameter("playerOneName");
        String playerTwoName = req.getParameter("playerTwoName");

        if (playerOneName != null && playerTwoName != null &&
                playerOneName.trim().equalsIgnoreCase(playerTwoName.trim())) {
            req.setAttribute("error", "Игрок не может играть сам с собой!");
            req.getRequestDispatcher("/WEB-INF/jsp/new-match.jsp").forward(req, resp);
            return;
        }

        Optional<Player> player1Opt = playerDao.findByName(playerOneName);
        Optional<Player> player2Opt = playerDao.findByName(playerTwoName);

        if (!player1Opt.isPresent()) {
            player1Opt = Optional.of(playerDao.save(new Player(null, playerOneName)));
        }
        if (!player2Opt.isPresent()) {
            player2Opt = Optional.of(playerDao.save(new Player(null, playerTwoName)));
        }

        Match match = new Match();
        match.setPlayer1(player1Opt.get());
        match.setPlayer2(player2Opt.get());

        UUID matchId = ongoingMatchesService.createMatch(match);
        System.out.println("NewMatchServlet: doPost called");
        System.out.println("Redirecting to: " + req.getContextPath() + "/match-score?uuid=" + matchId);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + matchId);
    }
}