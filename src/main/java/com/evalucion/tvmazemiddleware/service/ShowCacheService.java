package com.evalucion.tvmazemiddleware.service;

import com.evalucion.tvmazemiddleware.client.TvMazeClient;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ShowCacheService {

    private static final String COLLECTION = "shows";

    private final MongoTemplate mongoTemplate;
    private final TvMazeClient tvMazeClient;

    public ShowCacheService(MongoTemplate mongoTemplate, TvMazeClient tvMazeClient) {
        this.mongoTemplate = mongoTemplate;
        this.tvMazeClient = tvMazeClient;
    }

    public Map<String, Object> getShow(long showId) {
        Document cached = mongoTemplate.findById(showId, Document.class, COLLECTION);
        if (cached != null) {
            return withoutMongoId(cached);
        }
        Map<String, Object> show = tvMazeClient.getShow(showId);
        saveToCache(showId, show);
        return show;
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
