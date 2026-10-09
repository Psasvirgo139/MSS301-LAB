package com.fudn.movie_service.dto;

import com.fudn.movie_service.exception.ApiException;

public record GenreRequest(
        String genreName,
        String name,
        String description) {

    public String getEffectiveName() {
        if (genreName != null && !genreName.isBlank()) return genreName;
        if (name != null && !name.isBlank()) return name;
        throw ApiException.badRequest("Genre name cannot be empty");
    }
}
