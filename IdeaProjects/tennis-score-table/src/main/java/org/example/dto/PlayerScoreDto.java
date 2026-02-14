package org.example.dto;

import lombok.Data;
import org.example.model.RegularGamePoints;

@Data
public class PlayerScoreDto {
    private RegularGamePoints points = RegularGamePoints.LOVE;
    private int games = 0;
    private int sets = 0;
}
