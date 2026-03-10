package org.example.dao;


import org.example.entity.Player;

import java.util.Optional;

public interface PlayerDao extends Dao<Player, Integer> {

    Optional<Player> findByName(String name);
}
