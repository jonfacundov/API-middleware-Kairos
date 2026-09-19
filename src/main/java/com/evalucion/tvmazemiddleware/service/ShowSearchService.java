package com.evalucion.tvmazemiddleware.service;

import com.evalucion.tvmazemiddleware.client.TvMazeClient;
import com.evalucion.tvmazemiddleware.client.dto.TvMazeSearchResultDto;
import com.evalucion.tvmazemiddleware.client.dto.TvMazeShowDto;
import com.evalucion.tvmazemiddleware.dto.CommentSummaryDto;
import com.evalucion.tvmazemiddleware.dto.ShowSummaryDto;
import com.evalucion.tvmazemiddleware.repository.CommentRepository;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowSearchService {

    private final TvMazeClient tvMazeClient;
    private final CommentRepository commentRepository;

    public ShowSearchService(TvMazeClient tvMazeClient, CommentRepository commentRepository) {
        this.tvMazeClient = tvMazeClient;
        this.commentRepository = commentRepository;
    }

    public List<ShowSummaryDto> search(String query) {
        return tvMazeClient.search(query).stream()
                .map(this::toShowSummary)
                .toList();
    }

    private ShowSummaryDto toShowSummary(TvMazeSearchResultDto result) {
        TvMazeShowDto show = result.show();
        return new ShowSummaryDto(
                show.id(),
                show.name(),
                resolveChannel(show),
                stripHtml(show.summary()),
                show.genres(),
                comments(show.id())
        );
    }

    private List<CommentSummaryDto> comments(long showId) {
        return commentRepository.findByShowId(showId).stream()
                .map(comment -> new CommentSummaryDto(comment.getComment(), comment.getRating()))
                .toList();
    }

    // TV Maze reporta el canal en network (TV) o en webChannel (streaming), nunca en ambos.
    private String resolveChannel(TvMazeShowDto show) {
        if (show.network() != null) {
            return show.network().name();
        }
        if (show.webChannel() != null) {
            return show.webChannel().name();
        }
        return null;
    }

    private String stripHtml(String summary) {
        if (summary == null) {
            return null;
        }
        return Jsoup.parse(summary).text();
    }
}
