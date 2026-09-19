package com.evalucion.tvmazemiddleware.service;

import com.evalucion.tvmazemiddleware.dto.CommentRequestDto;
import com.evalucion.tvmazemiddleware.repository.CommentDocument;
import com.evalucion.tvmazemiddleware.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(long showId, CommentRequestDto request) {
        commentRepository.save(new CommentDocument(showId, request.comment(), request.rating()));
    }
}
