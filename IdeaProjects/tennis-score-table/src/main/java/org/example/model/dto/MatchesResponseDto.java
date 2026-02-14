package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Match;

import java.util.List;
@Data
@AllArgsConstructor

public class MatchesResponseDto {
    private List<Match> matches;
    private String notFoundMessage;
    private List<Integer> pagesToShow;
    private int currentPage;
    private int lastPageNumber;
}
