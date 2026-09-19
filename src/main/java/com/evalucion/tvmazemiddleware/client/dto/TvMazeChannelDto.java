package com.evalucion.tvmazemiddleware.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TvMazeChannelDto(String name) {
}
