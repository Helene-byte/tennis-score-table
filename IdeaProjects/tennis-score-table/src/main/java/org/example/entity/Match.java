package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@Table(name = "Matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player1", referencedColumnName = "id", nullable = false)
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2", referencedColumnName = "id", nullable = false)
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner", referencedColumnName = "id", nullable = false)
    private Player winner;
}