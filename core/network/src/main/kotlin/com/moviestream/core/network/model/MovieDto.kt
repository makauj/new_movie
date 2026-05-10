package com.moviestream.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Movie/TV Show DTOs

@Serializable
data class MovieDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
    @SerialName("poster")
    val poster: String?,
    @SerialName("backdrop")
    val backdrop: String?,
    @SerialName("rating")
    val rating: Double?,
    @SerialName("releaseDate")
    val releaseDate: String?,
    @SerialName("genres")
    val genres: List<String>?,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("director")
    val director: String?,
    @SerialName("cast")
    val cast: List<String>?,
    @SerialName("type")
    val type: String?, // "movie" or "show"
    @SerialName("imdbRating")
    val imdbRating: String?
)

@Serializable
data class MovieListResponse(
    @SerialName("movies")
    val movies: List<MovieDto>,
    @SerialName("totalPages")
    val totalPages: Int?,
    @SerialName("currentPage")
    val currentPage: Int?
)

@Serializable
data class MovieDetailResponse(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
    @SerialName("poster")
    val poster: String?,
    @SerialName("backdrop")
    val backdrop: String?,
    @SerialName("rating")
    val rating: Double?,
    @SerialName("releaseDate")
    val releaseDate: String?,
    @SerialName("genres")
    val genres: List<String>?,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("director")
    val director: String?,
    @SerialName("cast")
    val cast: List<CastMemberDto>?,
    @SerialName("imdbRating")
    val imdbRating: String?,
    @SerialName("similar")
    val similar: List<MovieDto>?,
    @SerialName("trailer")
    val trailer: String?,
    @SerialName("seasons")
    val seasons: List<SeasonDto>?
)

@Serializable
data class CastMemberDto(
    @SerialName("name")
    val name: String,
    @SerialName("character")
    val character: String?,
    @SerialName("image")
    val image: String?
)

@Serializable
data class SeasonDto(
    @SerialName("seasonNumber")
    val seasonNumber: Int,
    @SerialName("episodes")
    val episodes: List<EpisodeDto>
)

@Serializable
data class EpisodeDto(
    @SerialName("episodeNumber")
    val episodeNumber: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
    @SerialName("airDate")
    val airDate: String?,
    @SerialName("poster")
    val poster: String?,
    @SerialName("runtime")
    val runtime: Int?
)

@Serializable
data class SearchResponse(
    @SerialName("results")
    val results: List<MovieDto>,
    @SerialName("query")
    val query: String,
    @SerialName("totalResults")
    val totalResults: Int
)

@Serializable
data class GenreListResponse(
    @SerialName("genres")
    val genres: List<GenreDto>
)

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String
)
