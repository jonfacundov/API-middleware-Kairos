package com.evalucion.tvmazemiddleware.service;

import com.evalucion.tvmazemiddleware.dto.CommentRequestDto;
import com.evalucion.tvmazemiddleware.dto.CommentSummaryDto;
import com.evalucion.tvmazemiddleware.repository.CommentDocument;
import com.evalucion.tvmazemiddleware.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(long showId, CommentRequestDto request) {
        commentRepository.save(new CommentDocument(showId, request.comment(), request.rating()));
    }

    public List<CommentSummaryDto> findSummaries(long showId) {
        return commentRepository.findByShowId(showId).stream()
                .map(this::toSummary)
                .toList();
    }

    // Trae los comentarios de varios shows en una sola consulta, en vez de una por show.
    public Map<Long, List<CommentSummaryDto>> findSummariesByShowIds(List<Long> showIds) {
        return commentRepository.findByShowIdIn(showIds).stream()
                .collect(Collectors.groupingBy(
                        CommentDocument::getShowId,
                        Collectors.mapping(this::toSummary, Collectors.toList())
                ));
    }

    private CommentSummaryDto toSummary(CommentDocument comment) {
        return new CommentSummaryDto(comment.getComment(), comment.getRating());
    }
}
