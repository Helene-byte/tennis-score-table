package org.example.model.dto;

import lombok.Data;

@Data
public class PlayerScore {
    private RegularGamePoints points = RegularGamePoints.LOVE;
    private int games = 0;
    private int sets = 0;
}
