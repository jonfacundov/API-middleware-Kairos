package com.evalucion.tvmazemiddleware.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(
        @NotBlank String comment,
        @Min(0) @Max(5) int rating
) {
}
