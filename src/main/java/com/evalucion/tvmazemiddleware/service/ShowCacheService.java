package com.evalucion.tvmazemiddleware.service;

import com.evalucion.tvmazemiddleware.client.TvMazeClient;
import com.evalucion.tvmazemiddleware.dto.CommentSummaryDto;
import com.evalucion.tvmazemiddleware.repository.CommentRepository;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowCacheService {

    private static final String COLLECTION = "shows";

    private final MongoTemplate mongoTemplate;
    private final TvMazeClient tvMazeClient;
    private final CommentRepository commentRepository;

    public ShowCacheService(MongoTemplate mongoTemplate, TvMazeClient tvMazeClient,
                             CommentRepository commentRepository) {
        this.mongoTemplate = mongoTemplate;
        this.tvMazeClient = tvMazeClient;
        this.commentRepository = commentRepository;
    }

    public Map<String, Object> getShow(long showId) {
        Document cached = mongoTemplate.findById(showId, Document.class, COLLECTION);
        Map<String, Object> show = cached != null ? withoutMongoId(cached) : fetchAndCache(showId);
        show.put("comments", comments(showId));
        return show;
    }

    private Map<String, Object> fetchAndCache(long showId) {
        Map<String, Object> show = tvMazeClient.getShow(showId);
        saveToCache(showId, show);
        return show;
    }

    private List<CommentSummaryDto> comments(long showId) {
        return commentRepository.findByShowId(showId).stream()
                .map(comment -> new CommentSummaryDto(comment.getComment(), comment.getRating()))
                .toList();
    }

    private void saveToCache(long showId, Map<String, Object> show) {
        Document document = new Document(show);
        document.put("_id", showId);
        mongoTemplate.insert(document, COLLECTION);
    }

    // El _id es un detalle interno del cache, TV Maze nunca lo devuelve.
    private Map<String, Object> withoutMongoId(Document document) {
        Map<String, Object> copy = new LinkedHashMap<>(document);
        copy.remove("_id");
        return copy;
    }
}
