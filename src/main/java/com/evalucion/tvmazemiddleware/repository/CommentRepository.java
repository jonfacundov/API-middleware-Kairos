package com.evalucion.tvmazemiddleware.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentDocument, String> {

    List<CommentDocument> findByShowId(long showId);
}
