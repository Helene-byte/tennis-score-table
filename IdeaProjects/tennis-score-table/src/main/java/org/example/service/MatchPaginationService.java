package org.example.service;

import org.example.entity.Match;
import org.example.model.MatchPageResult;

import java.util.ArrayList;
import java.util.List;

public class MatchPaginationService {
    private static final int PAGE_SIZE = 3;
    private static MatchPaginationService instance;
    private final FinishedMatchesSearchService searchService;

    private MatchPaginationService(FinishedMatchesSearchService searchService) {
        this.searchService = searchService;
    }

    public static MatchPaginationService getInstance(FinishedMatchesSearchService searchService) {
        if (instance == null) {
            instance = new MatchPaginationService(searchService);
        }
        return instance;
    }
    public MatchPageResult getMatchesPageAndLastPageNumber(int pageNumber) {
        int totalMatches = searchService.countMatches(null);
        int lastPageNumber = (int) Math.ceil((double) totalMatches / PAGE_SIZE);
        int offset = (pageNumber - 1) * PAGE_SIZE;
        List<Match> matches = searchService.findMatches(null, offset, PAGE_SIZE);
        return new MatchPageResult(matches, lastPageNumber);
    }

    public MatchPageResult getMatchesPageAndLastPageNumberByName(String name, int pageNumber) {
        int totalMatches = searchService.countMatches(name);
        int lastPageNumber = (int) Math.ceil((double) totalMatches / PAGE_SIZE);
        int offset = (pageNumber - 1) * PAGE_SIZE;
        List<Match> matches = searchService.findMatches(name, offset, PAGE_SIZE);
        return new MatchPageResult(matches, lastPageNumber);
    }

    public List<Integer> getPagesToShow(int lastPageNumber, int currentPage) {
        // Simple implementation: show up to 5 pages around current
        int start = Math.max(1, currentPage - 2);
        int end = Math.min(lastPageNumber, currentPage + 2);
        List<Integer> pages = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            pages.add(i);
        }
        return pages;
    }
}
