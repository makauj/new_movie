package com.moviestream.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moviestream.feature.player.WebPlayerRouteArgs
import com.moviestream.feature.player.WebPlayerScreen

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}

object AuthScreen {
    const val LOGIN = "login_screen"
    const val SIGNUP = "signup_screen"
    const val FORGOT_PASSWORD = "forgot_password_screen"
    const val SPLASH = "splash_screen"
}

object MainScreen {
    const val HOME = "home_screen"
    const val SEARCH = "search_screen"
    const val DETAILS = "details_screen/{id}"
    const val PLAYER = "player_screen/{id}?mediaType={mediaType}&season={season}&episode={episode}"
    const val WATCHLIST = "watchlist_screen"
    const val PROFILE = "profile_screen"
    const val SETTINGS = "settings_screen"
}

fun playerRoute(
    id: String,
    mediaType: String = "movie",
    season: Int = 1,
    episode: Int = 1
): String {
    return "player_screen/${Uri.encode(id)}?mediaType=${Uri.encode(mediaType)}&season=$season&episode=$episode"
}

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(navController = navController)
        mainNavGraph(navController = navController)
    }
}

private fun androidx.navigation.NavGraphBuilder.authNavGraph(navController: NavHostController) {
    androidx.navigation.navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.SPLASH
    ) {
        composable(route = AuthScreen.SPLASH) {
            // Splash screen
        }
        composable(route = AuthScreen.LOGIN) {
            // Login screen
        }
        composable(route = AuthScreen.SIGNUP) {
            // Sign up screen
        }
        composable(route = AuthScreen.FORGOT_PASSWORD) {
            // Forgot password screen
        }
    }
}

private fun androidx.navigation.NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    androidx.navigation.navigation(
        route = Graph.MAIN,
        startDestination = MainScreen.HOME
    ) {
        composable(route = MainScreen.HOME) {
            // Home screen
        }
        composable(route = MainScreen.SEARCH) {
            // Search screen
        }
        composable(route = MainScreen.DETAILS) {
            // Details screen
        }
        composable(
            route = MainScreen.PLAYER,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("mediaType") {
                    type = NavType.StringType
                    defaultValue = "movie"
                },
                navArgument("season") {
                    type = NavType.IntType
                    defaultValue = 1
                },
                navArgument("episode") {
                    type = NavType.IntType
                    defaultValue = 1
                }
            )
        ) { backStackEntry ->
            val tmdbId = backStackEntry.arguments?.getString("id").orEmpty()
            val mediaType = backStackEntry.arguments?.getString("mediaType") ?: "movie"
            val season = backStackEntry.arguments?.getInt("season") ?: 1
            val episode = backStackEntry.arguments?.getInt("episode") ?: 1

            WebPlayerScreen(
                args = WebPlayerRouteArgs(
                    tmdbId = tmdbId,
                    mediaType = mediaType,
                    season = season,
                    episode = episode
                )
            )
        }
        composable(route = MainScreen.WATCHLIST) {
            // Watchlist screen
        }
        composable(route = MainScreen.PROFILE) {
            // Profile screen
        }
        composable(route = MainScreen.SETTINGS) {
            // Settings screen
        }
    }
}
