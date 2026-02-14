package org.example.mapper;

import org.example.model.MatchScore;
import org.example.dto.ScoreDto;
import org.modelmapper.ModelMapper;

public class ScoreMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static ScoreDto convertToDto(MatchScore score) {
        return modelMapper.map(score, ScoreDto.class);
    }
}
