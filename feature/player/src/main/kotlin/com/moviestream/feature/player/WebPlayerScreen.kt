package com.moviestream.feature.player

import android.annotation.SuppressLint
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.moviestream.core.common.player.VidKingWebPlayer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

data class WebPlayerRouteArgs(
    val tmdbId: String,
    val mediaType: String = "movie",
    val season: Int = 1,
    val episode: Int = 1,
    val title: String = ""
)

@Composable
fun WebPlayerScreen(
    args: WebPlayerRouteArgs,
    modifier: Modifier = Modifier,
    onPlayerEvent: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var statusText by remember { mutableStateOf("Loading player...") }
    val embedUrl = remember(args) {
        if (args.mediaType.equals("tv", ignoreCase = true)) {
            VidKingWebPlayer.tvUrl(args.tmdbId, args.season, args.episode)
        } else {
            VidKingWebPlayer.movieUrl(args.tmdbId)
        }
    }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    WebView(context).apply {
                        configurePlayerWebView(
                            onLoadingChanged = { loading, status ->
                                isLoading = loading
                                statusText = status
                            },
                            onConsoleEvent = { eventText ->
                                onPlayerEvent(eventText)
                            }
                        )
                        loadUrl(embedUrl)
                    }
                },
                update = { webView ->
                    if (webView.url != embedUrl) {
                        webView.loadUrl(embedUrl)
                    }
                }
            )

            if (isLoading) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp)
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
private fun WebView.configurePlayerWebView(
    onLoadingChanged: (Boolean, String) -> Unit,
    onConsoleEvent: (String) -> Unit
) {
    settings.apply {
        javaScriptEnabled = true
        domStorageEnabled = true
        mediaPlaybackRequiresUserGesture = false
        cacheMode = WebSettings.LOAD_DEFAULT
        loadWithOverviewMode = true
        useWideViewPort = true
    }

    webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
            onLoadingChanged(true, "Loading player...")
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            onLoadingChanged(false, "Player ready")
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }
    }

    webChromeClient = object : WebChromeClient() {
        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
            val message = consoleMessage.message()
            if (message.contains("PLAYER_EVENT") || message.contains("Message received from the player")) {
                onConsoleEvent(message)
            }
            return super.onConsoleMessage(consoleMessage)
        }
    }

    setBackgroundColor(android.graphics.Color.BLACK)
}

fun parsePlayerConsoleMessage(message: String): PlayerConsoleEvent? {
    val jsonCandidate = message.substringAfterLast(":", message).trim()
    return runCatching {
        val jsonElement = Json.parseToJsonElement(jsonCandidate)
        val jsonObject = jsonElement.jsonObject
        val type = jsonObject["type"]?.jsonPrimitive?.contentOrNull ?: return null
        if (type != "PLAYER_EVENT") return null

        PlayerConsoleEvent(
            event = jsonObject["data"]?.jsonObject?.get("event")?.jsonPrimitive?.contentOrNull.orEmpty(),
            rawMessage = message
        )
    }.getOrNull()
}

data class PlayerConsoleEvent(
    val event: String,
    val rawMessage: String
)