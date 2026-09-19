package com.evalucion.tvmazemiddleware.service;

import com.evalucion.tvmazemiddleware.client.TvMazeClient;
import com.evalucion.tvmazemiddleware.client.dto.TvMazeSearchResultDto;
import com.evalucion.tvmazemiddleware.client.dto.TvMazeShowDto;
import com.evalucion.tvmazemiddleware.dto.CommentSummaryDto;
import com.evalucion.tvmazemiddleware.dto.ShowSummaryDto;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShowSearchService {

    private final TvMazeClient tvMazeClient;
    private final CommentService commentService;

    public ShowSearchService(TvMazeClient tvMazeClient, CommentService commentService) {
        this.tvMazeClient = tvMazeClient;
        this.commentService = commentService;
    }

    public List<ShowSummaryDto> search(String query) {
        List<TvMazeSearchResultDto> results = tvMazeClient.search(query);
        Map<Long, List<CommentSummaryDto>> commentsByShowId = commentService.findSummariesByShowIds(showIds(results));
        return results.stream()
                .map(result -> toShowSummary(result, commentsByShowId))
                .toList();
    }

    private List<Long> showIds(List<TvMazeSearchResultDto> results) {
        return results.stream()
                .map(result -> result.show().id())
                .toList();
    }

    private ShowSummaryDto toShowSummary(TvMazeSearchResultDto result, Map<Long, List<CommentSummaryDto>> commentsByShowId) {
        TvMazeShowDto show = result.show();
        return new ShowSummaryDto(
                show.id(),
                show.name(),
                resolveChannel(show),
                stripHtml(show.summary()),
                show.genres(),
                commentsByShowId.getOrDefault(show.id(), List.of())
        );
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
