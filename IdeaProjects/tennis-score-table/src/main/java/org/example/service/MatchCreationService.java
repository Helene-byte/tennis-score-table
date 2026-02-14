package org.example.service;

import org.example.dao.PlayerDao;
import org.example.entity.Match;
import org.example.entity.Player;

import java.util.UUID;

public class MatchCreationService {
    private final PlayerDao playerDao;
    private final OngoingMatchesService ongoingMatchesService;

    public MatchCreationService(PlayerDao playerDao, OngoingMatchesService ongoingMatchesService) {
        this.playerDao = playerDao;
        this.ongoingMatchesService = ongoingMatchesService;
    }

    public UUID createNewMatch(String playerOneName, String playerTwoName) {
        if (playerOneName.equalsIgnoreCase(playerTwoName)) {
            throw new IllegalArgumentException("Игрок не может играть сам с собой!");
        }
        Player player1 = playerDao.findByName(playerOneName).orElseGet(() -> playerDao.save(new Player(null, playerOneName)));
        Player player2 = playerDao.findByName(playerTwoName).orElseGet(() -> playerDao.save(new Player(null, playerTwoName)));
        Match match = new Match();
        match.setPlayer1(player1);
        match.setPlayer2(player2);
        return ongoingMatchesService.createMatch(match);
    }
}
