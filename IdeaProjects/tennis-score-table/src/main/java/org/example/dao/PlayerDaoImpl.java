package org.example.dao;

import org.example.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class PlayerDaoImpl extends AbstractDao<Player, Integer> implements PlayerDao {
    public PlayerDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Player.class);
    }

    @Override
    public Optional<Player> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Player player = session.createQuery("FROM Player WHERE name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResult();
            return Optional.ofNullable(player);
        }
    }
}
