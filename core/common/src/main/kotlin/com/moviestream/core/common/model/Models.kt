package com.moviestream.core.common.model

data class Movie(
    val id: String,
    val title: String,
    val description: String?,
    val poster: String?,
    val backdrop: String?,
    val rating: Double?,
    val releaseDate: String?,
    val genres: List<String>?,
    val runtime: Int?,
    val director: String?,
    val cast: List<String>?,
    val type: String?, // "movie" or "show"
    val imdbRating: String?
)

data class MovieDetails(
    val id: String,
    val title: String,
    val description: String?,
    val poster: String?,
    val backdrop: String?,
    val rating: Double?,
    val releaseDate: String?,
    val genres: List<String>?,
    val runtime: Int?,
    val director: String?,
    val cast: List<CastMember>?,
    val imdbRating: String?,
    val similar: List<Movie>?,
    val trailer: String?,
    val seasons: List<Season>?
)

data class CastMember(
    val name: String,
    val character: String?,
    val image: String?
)

data class Season(
    val seasonNumber: Int,
    val episodes: List<Episode>
)

data class Episode(
    val episodeNumber: Int,
    val title: String,
    val description: String?,
    val airDate: String?,
    val poster: String?,
    val runtime: Int?
)

data class Genre(
    val id: String,
    val name: String
)

data class User(
    val id: String,
    val username: String,
    val email: String,
    val profileImage: String?
)

data class WatchProgress(
    val movieId: String,
    val watchedDurationMs: Long,
    val totalDurationMs: Long
)
