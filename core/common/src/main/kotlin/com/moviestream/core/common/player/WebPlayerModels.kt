package com.moviestream.core.common.player

data class WebPlayerUrls(
    val movieUrl: String,
    val tvUrl: String
)

data class PlayerEventEnvelope(
    val type: String,
    val data: PlayerEventData
)

data class PlayerEventData(
    val event: String,
    val currentTime: Double,
    val duration: Double,
    val progress: Double,
    val id: String,
    val mediaType: String,
    val season: Int? = null,
    val episode: Int? = null,
    val timestamp: Long
)

object VidKingWebPlayer {
    private const val BASE_URL = "https://www.vidking.net"
    private const val DEFAULT_COLOR = "ff0000"

    fun movieUrl(tmdbId: String): String {
        return "$BASE_URL/embed/movie/$tmdbId?color=$DEFAULT_COLOR&autoPlay=true&nextEpisode=true&episodeSelector=true"
    }

    fun tvUrl(tmdbId: String, season: Int, episode: Int): String {
        return "$BASE_URL/embed/tv/$tmdbId/$season/$episode"
    }

    fun urlsForMedia(tmdbId: String, season: Int? = null, episode: Int? = null): WebPlayerUrls {
        val tvSeason = season ?: 1
        val tvEpisode = episode ?: 1

        return WebPlayerUrls(
            movieUrl = movieUrl(tmdbId),
            tvUrl = tvUrl(tmdbId, tvSeason, tvEpisode)
        )
    }

    fun progressTrackingScript(): String {
        return """
            window.addEventListener(\"message\", function (event) {
              if (typeof event.data === \"string\") {
                var messageArea = document.querySelector(\"#messageArea\");
                if (messageArea) {
                  messageArea.innerText = event.data;
                }
              }

              try {
                console.log(\"Message received from the player:\", JSON.parse(event.data));
              } catch (error) {
                console.log(\"Message received from the player:\", event.data);
              }
            });
        """.trimIndent()
    }
}