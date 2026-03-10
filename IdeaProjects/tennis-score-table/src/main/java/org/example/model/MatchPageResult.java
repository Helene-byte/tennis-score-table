package org.example.model;

import org.example.entity.Match;

import java.util.List;

public record MatchPageResult(List<Match> matchesPage, int lastPageNumber) {
}
