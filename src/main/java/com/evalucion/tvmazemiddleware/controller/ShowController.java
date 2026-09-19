package com.evalucion.tvmazemiddleware.controller;

import com.evalucion.tvmazemiddleware.client.TvMazeClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final TvMazeClient tvMazeClient;

    public ShowController(TvMazeClient tvMazeClient) {
        this.tvMazeClient = tvMazeClient;
    }

    @GetMapping("/{showId}")
    public ResponseEntity<Map<String, Object>> getShow(@PathVariable long showId) {
        return ResponseEntity.ok(tvMazeClient.getShow(showId));
    }
}
