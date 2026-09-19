package com.evalucion.tvmazemiddleware.dto;

import java.util.List;

public record ShowSummaryDto(
        long id,
        String name,
        String channel,
        String summary,
        List<String> genres
) {
}
