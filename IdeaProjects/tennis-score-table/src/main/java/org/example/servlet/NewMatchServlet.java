package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.PlayerDao;
import org.example.dao.PlayerDaoImpl;
import org.example.model.dto.MatchScoreModel;
import org.example.model.entity.Match;
import org.example.model.entity.Player;
import org.example.service.OngoingMatchesService;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private PlayerDao playerDao;

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = (SessionFactory) getServletContext().getAttribute("sessionFactory");
        playerDao = new PlayerDaoImpl(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerOneName = req.getParameter("playerOneName");
        String playerTwoName = req.getParameter("playerTwoName");
        Map<String, String> errors = new HashMap<>();

        if (playerOneName == null || playerOneName.trim().isEmpty()) {
            errors.put("playerOneNameNotValid", "Player 1 name is required.");
        }
        if (playerTwoName == null || playerTwoName.trim().isEmpty()) {
            errors.put("playerTwoNameNotValid", "Player 2 name is required.");
        }
        if (playerOneName != null && playerTwoName != null && playerOneName.equals(playerTwoName)) {
            errors.put("playerNamesAreSame", "Players must be different.");
        }

        Optional<Player> player1Opt = playerDao.findByName(playerOneName);
        Optional<Player> player2Opt = playerDao.findByName(playerTwoName);

        if (!player1Opt.isPresent()) {
            player1Opt = Optional.of(playerDao.save(new Player(null, playerOneName)));
        }
        if (!player2Opt.isPresent()) {
            player2Opt = Optional.of(playerDao.save(new Player(null, playerTwoName)));
        }

        if (!errors.isEmpty()) {
            req.setAttribute("playerOneName", playerOneName);
            req.setAttribute("playerTwoName", playerTwoName);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
            return;
        }

        Match match = new Match();
        match.setPlayer1(player1Opt.get());
        match.setPlayer2(player2Opt.get());

        UUID matchId = ongoingMatchesService.createMatch(match);

        resp.sendRedirect("match-score?uuid=" + matchId);
    }
}
