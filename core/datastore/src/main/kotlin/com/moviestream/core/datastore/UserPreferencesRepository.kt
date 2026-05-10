package com.moviestream.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userPreferencesDataStore by preferencesDataStore(
    name = "user_preferences"
)

object PreferencesKeys {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    val USER_ID = stringPreferencesKey("user_id")
    val USERNAME = stringPreferencesKey("username")
    val EMAIL = stringPreferencesKey("email")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val THEME_MODE = stringPreferencesKey("theme_mode") // "dark", "light", "system"
    val SUBTITLE_LANGUAGE = stringPreferencesKey("subtitle_language")
    val VIDEO_QUALITY = stringPreferencesKey("video_quality") // "720p", "1080p", "2160p", "auto"
    val PLAYBACK_SPEED = stringPreferencesKey("playback_speed") // Default: "1.0"
    val AUTO_PLAY_NEXT = booleanPreferencesKey("auto_play_next")
    val SHOW_INTRO_SKIP = booleanPreferencesKey("show_intro_skip")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val LAST_PLAYED_MOVIE_ID = stringPreferencesKey("last_played_movie_id")
    val CONTINUE_WATCHING_TIME = intPreferencesKey("continue_watching_time")
}

data class UserPreferences(
    val authToken: String = "",
    val userId: String = "",
    val username: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val themeMode: String = "dark",
    val subtitleLanguage: String = "en",
    val videoQuality: String = "auto",
    val playbackSpeed: String = "1.0",
    val autoPlayNext: Boolean = true,
    val showIntroSkip: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val lastPlayedMovieId: String = "",
    val continueWatchingTime: Int = 0
)

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.userPreferencesDataStore

    val userPreferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        UserPreferences(
            authToken = preferences[PreferencesKeys.AUTH_TOKEN] ?: "",
            userId = preferences[PreferencesKeys.USER_ID] ?: "",
            username = preferences[PreferencesKeys.USERNAME] ?: "",
            email = preferences[PreferencesKeys.EMAIL] ?: "",
            isLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false,
            themeMode = preferences[PreferencesKeys.THEME_MODE] ?: "dark",
            subtitleLanguage = preferences[PreferencesKeys.SUBTITLE_LANGUAGE] ?: "en",
            videoQuality = preferences[PreferencesKeys.VIDEO_QUALITY] ?: "auto",
            playbackSpeed = preferences[PreferencesKeys.PLAYBACK_SPEED] ?: "1.0",
            autoPlayNext = preferences[PreferencesKeys.AUTO_PLAY_NEXT] ?: true,
            showIntroSkip = preferences[PreferencesKeys.SHOW_INTRO_SKIP] ?: true,
            notificationsEnabled = preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: true,
            lastPlayedMovieId = preferences[PreferencesKeys.LAST_PLAYED_MOVIE_ID] ?: "",
            continueWatchingTime = preferences[PreferencesKeys.CONTINUE_WATCHING_TIME] ?: 0
        )
    }

    suspend fun setAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] = token
        }
    }

    suspend fun setUserInfo(userId: String, username: String, email: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
            preferences[PreferencesKeys.USERNAME] = username
            preferences[PreferencesKeys.EMAIL] = email
            preferences[PreferencesKeys.IS_LOGGED_IN] = true
        }
    }

    suspend fun setThemeMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = mode
        }
    }

    suspend fun setSubtitleLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SUBTITLE_LANGUAGE] = language
        }
    }

    suspend fun setVideoQuality(quality: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.VIDEO_QUALITY] = quality
        }
    }

    suspend fun setPlaybackSpeed(speed: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PLAYBACK_SPEED] = speed
        }
    }

    suspend fun setAutoPlayNext(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTO_PLAY_NEXT] = enabled
        }
    }

    suspend fun setShowIntroSkip(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_INTRO_SKIP] = enabled
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setLastPlayedMovie(movieId: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_PLAYED_MOVIE_ID] = movieId
        }
    }

    suspend fun setContinueWatchingTime(timeMs: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CONTINUE_WATCHING_TIME] = timeMs
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
