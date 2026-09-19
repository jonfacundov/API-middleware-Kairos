package com.evalucion.tvmazemiddleware.controller;

import com.evalucion.tvmazemiddleware.dto.CommentRequestDto;
import com.evalucion.tvmazemiddleware.dto.StatusResponseDto;
import com.evalucion.tvmazemiddleware.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shows")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{showId}/comments")
    public ResponseEntity<StatusResponseDto> addComment(@PathVariable long showId,
                                                          @Valid @RequestBody CommentRequestDto request) {
        commentService.save(showId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusResponseDto("guardado"));
    }
}
