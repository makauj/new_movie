package com.moviestream.core.network.repository

import com.moviestream.core.common.Result
import com.moviestream.core.common.model.CastMember
import com.moviestream.core.common.model.Episode
import com.moviestream.core.common.model.Genre
import com.moviestream.core.common.model.Movie
import com.moviestream.core.common.model.MovieDetails
import com.moviestream.core.common.model.Season
import com.moviestream.core.common.repository.MovieRepository
import com.moviestream.core.network.api.CinebytApi
import com.moviestream.core.network.model.CastMemberDto
import com.moviestream.core.network.model.EpisodeDto
import com.moviestream.core.network.model.GenreDto
import com.moviestream.core.network.model.MovieDto
import com.moviestream.core.network.model.SeasonDto
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: CinebytApi
) : MovieRepository {

    override suspend fun getTrendingMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = api.getTrendingMovies(page = page)
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = api.getPopularMovies(page = page)
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = api.getTopRatedMovies(page = page)
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getNewReleases(page: Int): Result<List<Movie>> {
        return try {
            val response = api.getNewReleases(page = page)
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMovieDetails(movieId: String): Result<MovieDetails> {
        return try {
            val response = api.getMovieDetails(movieId)
            Result.Success(response.toDomain())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> {
        return try {
            val response = api.searchMovies(query = query, page = page)
            Result.Success(response.results.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return try {
            val response = api.getGenres()
            Result.Success(response.genres.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMoviesByGenre(genre: String, page: Int): Result<List<Movie>> {
        return try {
            val response = api.getMoviesByGenre(genre = genre, page = page)
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getSimilarMovies(movieId: String): Result<List<Movie>> {
        return try {
            val response = api.getSimilarMovies(movieId = movieId)
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTrendingTvShows(page: Int): Result<List<Movie>> {
        return try {
            val response = api.getTrendingTvShows(page = page)
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPopularTvShows(page: Int): Result<List<Movie>> {
        return try {
            val response = api.getPopularTvShows(page = page)
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // Mappers
    private fun MovieDto.toDomain(): Movie = Movie(
        id = id,
        title = title,
        description = description,
        poster = poster,
        backdrop = backdrop,
        rating = rating,
        releaseDate = releaseDate,
        genres = genres,
        runtime = runtime,
        director = director,
        cast = cast,
        type = type,
        imdbRating = imdbRating
    )

    private fun com.moviestream.core.network.model.MovieDetailResponse.toDomain(): MovieDetails = MovieDetails(
        id = id,
        title = title,
        description = description,
        poster = poster,
        backdrop = backdrop,
        rating = rating,
        releaseDate = releaseDate,
        genres = genres,
        runtime = runtime,
        director = director,
        cast = cast?.map { it.toDomain() },
        imdbRating = imdbRating,
        similar = similar?.map { it.toDomain() },
        trailer = trailer,
        seasons = seasons?.map { it.toDomain() }
    )

    private fun CastMemberDto.toDomain(): CastMember = CastMember(
        name = name,
        character = character,
        image = image
    )

    private fun SeasonDto.toDomain(): Season = Season(
        seasonNumber = seasonNumber,
        episodes = episodes.map { it.toDomain() }
    )

    private fun EpisodeDto.toDomain(): Episode = Episode(
        episodeNumber = episodeNumber,
        title = title,
        description = description,
        airDate = airDate,
        poster = poster,
        runtime = runtime
    )

    private fun GenreDto.toDomain(): Genre = Genre(
        id = id,
        name = name
    )
}
