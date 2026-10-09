package com.fudn.movie_service.dto;

import com.fudn.movie_service.model.AgeRating;
import com.fudn.movie_service.model.Movie;
import com.fudn.movie_service.model.MovieStatus;

import java.time.LocalDate;
import java.util.List;

public record MovieResponse(
        String id,
        String movieId,
        String title,
        String description,
        String director,
        Integer duration,
        Integer durationMinutes,
        String language,
        AgeRating ageRating,
        LocalDate releaseDate,
        String genreId,
        List<String> genreIds,
        String genreName,
        MovieStatus status,
        MovieStatus movieStatus) {

    public static MovieResponse from(Movie m, String genreName) {
        String gId = m.getGenreId();
        List<String> gIds = gId != null ? List.of(gId) : List.of();
        return new MovieResponse(
                m.getMovieId(),
                m.getMovieId(),
                m.getTitle(),
                m.getDescription(),
                m.getDirector(),
                m.getDurationMinutes(),
                m.getDurationMinutes(),
                m.getLanguage(),
                m.getAgeRating(),
                m.getReleaseDate(),
                gId,
                gIds,
                genreName,
                m.getMovieStatus(),
                m.getMovieStatus()
        );
    }
}

