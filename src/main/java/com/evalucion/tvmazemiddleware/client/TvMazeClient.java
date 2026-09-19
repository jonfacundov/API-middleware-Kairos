package com.evalucion.tvmazemiddleware.client;

import com.evalucion.tvmazemiddleware.client.dto.TvMazeSearchResultDto;
import com.evalucion.tvmazemiddleware.exception.ShowNotFoundException;
import com.evalucion.tvmazemiddleware.exception.TvMazeUnavailableException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Component
public class TvMazeClient {

    private final WebClient webClient;

    public TvMazeClient(WebClient tvMazeWebClient) {
        this.webClient = tvMazeWebClient;
    }

    public List<TvMazeSearchResultDto> search(String query) {
        try {
            // uriBuilder codifica el query param automaticamente, incluye acentos y espacios.
            TvMazeSearchResultDto[] results = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search/shows")
                            .queryParam("q", query)
                            .build())
                    .retrieve()
                    .bodyToMono(TvMazeSearchResultDto[].class)
                    .block();
            return results == null ? List.of() : List.of(results);
        } catch (WebClientResponseException | WebClientRequestException ex) {
            throw new TvMazeUnavailableException("No se pudo consultar TV Maze", ex);
        }
    }

    public Map<String, Object> getShow(long showId) {
        try {
            return webClient.get()
                    .uri("/shows/{showId}", showId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new ShowNotFoundException(showId);
        } catch (WebClientResponseException | WebClientRequestException ex) {
            throw new TvMazeUnavailableException("No se pudo consultar TV Maze", ex);
        }
    }
}
