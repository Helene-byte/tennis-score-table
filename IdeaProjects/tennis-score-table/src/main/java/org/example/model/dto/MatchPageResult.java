package org.example.model.dto;

import org.example.entity.Match;

import java.util.List;

public record MatchPageResult(List<Match> matchesPage, int lastPageNumber) {
}
