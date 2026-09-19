package com.evalucion.tvmazemiddleware.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TvMazeShowDto(
        long id,
        String name,
        String summary,
        List<String> genres,
        TvMazeChannelDto network,
        TvMazeChannelDto webChannel
) {
}
