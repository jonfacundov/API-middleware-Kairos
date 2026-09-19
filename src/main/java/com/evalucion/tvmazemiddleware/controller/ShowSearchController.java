package com.evalucion.tvmazemiddleware.controller;

import com.evalucion.tvmazemiddleware.dto.ShowSummaryDto;
import com.evalucion.tvmazemiddleware.service.ShowSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowSearchController {

    private final ShowSearchService showSearchService;

    public ShowSearchController(ShowSearchService showSearchService) {
        this.showSearchService = showSearchService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShowSummaryDto>> search(@RequestParam String query) {
        if (query.isBlank()) {
            throw new IllegalArgumentException("El parametro query es obligatorio");
        }
        return ResponseEntity.ok(showSearchService.search(query));
    }
}
