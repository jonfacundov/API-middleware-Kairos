package com.evalucion.tvmazemiddleware.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class CommentDocument {

    @Id
    private String id;
    private final long showId;
    private final String comment;
    private final int rating;

    public CommentDocument(long showId, String comment, int rating) {
        this.showId = showId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public long getShowId() {
        return showId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }
}
