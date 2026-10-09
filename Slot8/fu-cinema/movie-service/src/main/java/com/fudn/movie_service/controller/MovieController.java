package com.fudn.movie_service.controller;

import com.fudn.movie_service.dto.MovieRequest;
import com.fudn.movie_service.dto.MovieResponse;
import com.fudn.movie_service.exception.ApiException;
import com.fudn.movie_service.model.MovieStatus;
import com.fudn.movie_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieResponse> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genreId,
            @RequestParam(required = false) String status) {
        String kw = title != null ? title : keyword;
        MovieStatus movieStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                movieStatus = MovieStatus.valueOf(status.toUpperCase());
            } catch (Exception ignored) {}
        }
        return movieService.search(kw, genreId, movieStatus);
    }

    @GetMapping("/search")
    public List<MovieResponse> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genreId,
            @RequestParam(required = false) String status) {
        return getAll(title, keyword, genreId, status);
    }

    @GetMapping("/{id}")
    public MovieResponse getById(@PathVariable String id) {
        return movieService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponse create(@RequestBody MovieRequest request) {
        return movieService.create(request);
    }

    @PutMapping("/{id}")
    public MovieResponse update(@PathVariable String id, @RequestBody MovieRequest request) {
        return movieService.update(id, request);
    }

    @PutMapping("/{id}/status")
    public MovieResponse updateStatus(@PathVariable String id, @RequestParam String status) {
        MovieStatus movieStatus;
        try {
            movieStatus = MovieStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw ApiException.badRequest("Invalid status: " + status);
        }
        return movieService.updateStatus(id, movieStatus);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        movieService.delete(id);
    }
}

