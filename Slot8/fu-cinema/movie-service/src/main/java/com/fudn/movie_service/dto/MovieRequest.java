package com.fudn.movie_service.dto;

import com.fudn.movie_service.exception.ApiException;
import com.fudn.movie_service.model.AgeRating;
import com.fudn.movie_service.model.MovieStatus;

import java.time.LocalDate;

public record MovieRequest(
        String title,
        String description,
        String director,
        Integer duration,
        Integer durationMinutes,
        String language,
        Object ageRating,
        LocalDate releaseDate,
        String genreId,
        Object genreIds,
        Object status,
        Object movieStatus,
        String posterUrl) {

    public String getEffectiveTitle() {
        if (title != null && !title.isBlank()) return title;
        throw ApiException.badRequest("Title is required");
    }

    public int getEffectiveDuration() {
        Integer d = duration != null ? duration : durationMinutes;
        if (d == null) throw ApiException.badRequest("Duration is required");
        if (d < 30 || d > 300) throw ApiException.badRequest("Duration must be between 30 and 300 minutes");
        return d;
    }

    public String getEffectiveGenreId() {
        if (genreId != null && !genreId.isBlank()) return genreId;
        if (genreIds != null) {
            if (genreIds instanceof java.util.List<?> list && !list.isEmpty()) {
                return String.valueOf(list.get(0));
            }
            if (genreIds instanceof String s && !s.isBlank()) {
                return s;
            }
        }
        throw ApiException.badRequest("Genre id is required");
    }

    public AgeRating getEffectiveAgeRating() {
        if (ageRating != null) {
            try {
                return AgeRating.valueOf(ageRating.toString().toUpperCase());
            } catch (Exception ignored) {}
        }
        return AgeRating.P;
    }

    public MovieStatus getEffectiveStatus() {
        Object s = status != null ? status : movieStatus;
        if (s != null) {
            try {
                return MovieStatus.valueOf(s.toString().toUpperCase());
            } catch (Exception ignored) {}
        }
        return MovieStatus.ACTIVE;
    }
}

