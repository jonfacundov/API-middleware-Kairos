package com.evalucion.tvmazemiddleware.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TvMazeSearchResultDto(double score, TvMazeShowDto show) {
}
