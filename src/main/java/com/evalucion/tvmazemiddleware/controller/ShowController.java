package com.evalucion.tvmazemiddleware.controller;

import com.evalucion.tvmazemiddleware.service.ShowCacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final ShowCacheService showCacheService;

    public ShowController(ShowCacheService showCacheService) {
        this.showCacheService = showCacheService;
    }

    @GetMapping("/{showId}")
    public ResponseEntity<Map<String, Object>> getShow(@PathVariable long showId) {
        return ResponseEntity.ok(showCacheService.getShow(showId));
    }
}
