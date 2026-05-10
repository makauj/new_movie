# MovieStream - Android Movie Streaming App

A production-quality Android movie streaming application built with modern architecture, inspired by Netflix, Cineby, Stremio, and Prime Video. This app uses the Cineby API for content delivery and follows Android best practices.

---

# Project Overview

Create a fully functional Android movie streaming app that:

- Streams movies and TV shows
- Uses cineby.sc API endpoints
- Supports search, genres, trending content, recommendations
- Plays videos inside the app
- Has authentication and user profiles
- Supports favorites/watchlists
- Saves watch progress
- Has beautiful animations and responsive UI
- Uses modern Android development practices

The app should feel premium and smooth.

---

# Tech Stack Requirements

Use the following stack:

## Android Frontend

- Kotlin
- Jetpack Compose
- MVVM Architecture
- Clean Architecture
- Hilt/Dagger for dependency injection
- Retrofit/Ktor for API networking
- Coroutines + Flow
- Room Database for offline caching
- DataStore for preferences
- Navigation Compose
- Coil or Glide for image loading
- WebView / embed-player playback

## Backend (Optional if needed)

If cineby.sc requires proxying or token handling:

- Node.js + Express
- TypeScript
- Redis caching
- JWT authentication

## Other

- Firebase Analytics
- Firebase Crashlytics
- Firebase Authentication (optional)
- Paging 3 library
- Material 3 Design

---

# UI/UX Design Goals

Design a visually stunning streaming platform.

The design should include:

- Dark modern UI
- Smooth animations
- Large movie hero banners
- Horizontal scrolling movie rows
- Gradient overlays
- Auto-playing previews (optional)
- Glassmorphism effects where appropriate
- Responsive layouts
- Skeleton loading states
- Error states
- Empty states

Use inspiration from:

- Netflix
- Disney+
- Prime Video
- Cineby
- Apple TV

---

# Required Features

# 1. Authentication System

Implement:

- Sign up
- Login
- Guest mode
- Forgot password
- Persistent login sessions
- Multi-profile support

Use:

- Firebase Auth OR JWT authentication

---

# 2. Home Screen

The home screen should contain:

- Hero featured movie carousel
- Trending movies
- Popular movies
- Top rated
- Recently added
- Continue watching
- Recommended for you
- Genre categories

Each section should scroll horizontally.

---

# 3. Search System

Build advanced search functionality:

- Instant search suggestions
- Search history
- Search movies and TV shows
- Filter by:
  - Genre
  - Year
  - Rating
  - Language
  - Popularity

---

# 4. Movie Details Screen

Display:

- Backdrop image
- Poster
- Title
- Description
- Genres
- Rating
- Runtime
- Cast
- Director
- Trailer
- Similar movies
- Seasons/episodes for TV shows

Actions:

- Play
- Add to watchlist
- Download (optional)
- Share

---

# 5. Video Streaming Player

Use ExoPlayer/Media3.

Features:

- Adaptive streaming
- Fullscreen support
- Subtitles
- Multiple quality selection
- Playback speed controls
- Gesture controls
- Picture-in-picture
- Resume playback
- Intro skip button
- Auto next episode

---

# 6. Watchlist & Favorites

Allow users to:

- Save movies
- Remove movies
- Sync across devices
- Organize lists

---

# 7. Offline Caching

Implement:

- Room database caching
- Recently viewed
- Search history
- Watch progress
- API response caching

---

# 8. Recommendation Engine

Use:

- Similar genres
- User watch history
- Trending algorithms

Create personalized recommendations.

---

# 9. Settings Screen

Include:

- Theme selection
- Subtitle preferences
- Video quality preference
- Account management
- Cache management
- Notifications

---

# 10. Notifications

Push notifications for:

- New releases
- Recommendations
- Continue watching reminders

Use Firebase Cloud Messaging.

---

# API Integration Requirements

Study and integrate cineby.sc API thoroughly. (api documentation can be found at https://www.vidking.net/#documentation)

The AI should:

1. Analyze all API endpoints
2. Create Retrofit interfaces
3. Create repository layer
4. Handle:
   - Authentication tokens
   - Headers
   - Errors
   - Rate limits
   - Pagination

Generate:

- DTO models
- Domain models
- Mapper classes

---

# Architecture Requirements

Use Clean Architecture:

## Layers

- Presentation
- Domain
- Data

## Modules

- authentication
- home
- search
- player
- watchlist
- profile
- settings
- core/common

Use:

- Repository pattern
- Use cases/interactors
- State management
- UI state classes

---

# Performance Requirements

Optimize for:

- Low memory usage
- Fast startup
- Smooth scrolling
- Image caching
- Lazy loading
- Efficient pagination

---

# Security Requirements

Implement:

- Secure API handling
- Encrypted preferences
- SSL pinning if possible
- Token refresh handling

---

# Deliverables Required From AI

Generate:

1. Full project structure
2. All Gradle configurations
3. Architecture setup
4. Complete source code
5. API integration layer
6. UI components
7. Navigation system
8. ExoPlayer integration
9. Database setup
10. Dependency injection setup
11. Authentication flow
12. Production-ready code
13. README documentation
14. Build instructions
15. Testing setup

---

# Code Quality Requirements

The generated code must:

- Follow SOLID principles
- Be modular and reusable
- Use Kotlin best practices
- Include comments where necessary
- Be production-ready
- Use proper naming conventions
- Handle loading/error states

---

# Additional Advanced Features (Optional but Preferred)

If possible, also implement:

- Chromecast support
- Torrent streaming support
- Download for offline viewing
- AI recommendations
- Voice search
- Continue watching sync
- Multi-language subtitles
- Trailer autoplay
- TV mode support
- Android TV compatibility

---

# Expected Output Format

I want the AI to generate:

1. Complete folder structure
2. Step-by-step implementation
3. Full code files
4. Explanations for architecture decisions
5. Commands to run/build
6. Environment configuration
7. API integration instructions
8. Deployment instructions

---

# Important Constraints

- Do NOT use deprecated Android APIs
- Use latest stable Android libraries
- App must support Android 10+
- UI must be modern and smooth
- Code should compile without major modifications
- Avoid placeholder code unless unavoidable

---

# Final Goal

The final app should look and behave like a premium streaming platform available on the Play Store, with excellent UX, smooth playback, scalable architecture, and maintainable code.

The AI should think like a senior Android engineer building a real-world streaming platform startup product.

---

# Project Implementation Guide

## Project Structure

```
MovieStream/
├── app/                              # Main application module
│   ├── src/main/
│   │   ├── kotlin/com/moviestream/
│   │   │   ├── MainActivity.kt
│   │   │   ├── MovieStreamApp.kt
│   │   │   ├── navigation/
│   │   │   └── ui/theme/
│   │   ├── AndroidManifest.xml
│   │   └── res/
│   └── build.gradle.kts
│
├── core/                             # Core modules
│   ├── common/                       # Common utilities, models, interfaces
│   │   ├── Result.kt
│   │   ├── UiState.kt
│   │   ├── model/Models.kt
│   │   ├── repository/MovieRepository.kt
│   │   └── Utils.kt
│   │
│   ├── network/                      # Network & API integration
│   │   ├── api/CinebytApi.kt
│   │   ├── model/MovieDto.kt
│   │   ├── repository/MovieRepositoryImpl.kt
│   │   └── di/NetworkModule.kt
│   │
│   ├── database/                     # Room Database
│   │   ├── MovieStreamDatabase.kt
│   │   ├── entity/Entities.kt
│   │   ├── dao/Daos.kt
│   │   └── di/DatabaseModule.kt
│   │
│   └── datastore/                    # DataStore for preferences
│       ├── UserPreferencesRepository.kt
│
├── feature/                          # Feature modules
│   ├── authentication/               # Auth flows
│   ├── home/                         # Home screen & discovery
│   ├── search/                       # Search functionality
│   ├── details/                      # Movie details screen
│   ├── player/                       # Video player
│   ├── watchlist/                    # Watchlist management
│   ├── profile/                      # User profile
│   └── settings/                     # App settings
│
├── build.gradle.kts                  # Root build config
├── settings.gradle.kts               # Project settings
└── README.md                         # This file
```

## Setup Instructions

### Prerequisites

- Android Studio Flamingo or later
- Android SDK 34+
- Kotlin 1.9.22+
- Gradle 8.2+
- Java 17+

### Clone and Open Project

```bash
# Clone the repository
git clone <repository-url>
cd MovieStream

# Open in Android Studio
# File > Open > Select project folder
```

### Configure Firebase (Optional)

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or select existing
3. Add Android app to project
4. Download `google-services.json`
5. Place in `app/` directory

### Build the Project

```bash
# Using Gradle wrapper
./gradlew clean build

# Or in Android Studio:
# Build > Clean Project
# Build > Rebuild Project
```

### Run the App

```bash
# Using ADB
./gradlew installDebug

# Or in Android Studio:
# Run > Run 'app'
```

---

## Architecture Overview

### Clean Architecture Layers

The app is structured using Clean Architecture with three main layers:

#### 1. **Presentation Layer** (`feature/*`)

- Contains UI (Compose screens), ViewModels, and UI state management
- Each feature has its own module for independence
- Uses MVVM pattern with StateFlow for reactive updates

#### 2. **Domain Layer** (`core/common`)

- Business logic and use cases
- Repository interfaces defining contracts
- Domain models (Movie, MovieDetails, etc.)
- Result and UiState sealed classes for state management

#### 3. **Data Layer** (`core/network`, `core/database`)

- Retrofit API service (CinebytApi)
- Room database for offline caching
- Repository implementations handling API & DB operations
- DTOs converted to domain models

### Dependency Injection (Hilt)

All modules use Hilt for dependency injection:

- `NetworkModule` provides Retrofit, OkHttp, and API service
- `DatabaseModule` provides Room database and DAOs
- `RepositoryModule` binds repository implementations
- `DatastoreModule` provides user preferences

### Navigation

Navigation is handled using Jetpack Compose Navigation:

- Root navigation graph with AUTH and MAIN sub-graphs
- Type-safe routing with sealed classes
- Deep linking support

---

## Key Technologies

### Android Framework

- **Kotlin** - Primary language
- **Jetpack Compose** - Modern UI framework
- **Navigation Compose** - App navigation
- **Hilt** - Dependency injection
- **Coroutines** - Async operations
- **Flow** - Reactive streams

### Networking & Data

- **Retrofit** - HTTP client
- **OkHttp** - HTTP logging and interceptors
- **Kotlinx Serialization** - JSON parsing
- **Room** - Local database
- **DataStore** - User preferences

### Media & UI

- **WebView embed player** - Video streaming and playback
- **Coil** - Image loading and caching
- **Material 3** - Design system
- **Paging 3** - List pagination

### Cloud Services

- **Firebase Authentication** - User auth
- **Firebase Analytics** - Event tracking
- **Firebase Crashlytics** - Error reporting
- **Firebase Cloud Messaging** - Push notifications

---

## Feature Modules Details

### Authentication Module

Handles user authentication flows:

- Login, Sign up, Guest mode
- Forgot password flow
- Session management
- Firebase Auth integration

### Home Module

Main discovery screen with:

- Featured movie carousel
- Trending content
- Popular movies & TV shows
- Recently added content
- Personalized recommendations
- Horizontal scrolling sections

### Search Module

Advanced search with:

- Real-time search suggestions
- Search history
- Filters (genre, year, rating, language)
- Both movies and TV shows support

### Details Module

Movie/Show information page:

- Backdrop, poster, title
- Description, genres, ratings
- Cast and crew info
- Trailer playback
- Similar recommendations
- TV show seasons & episodes

### Player Module

Web player based on embedded video pages:

- Embed URL construction for movie and TV playback
- Fullscreen mode
- Subtitle support
- Resume playback
- Intro skip and next episode controls via player events

### Progress Tracking Script

Use this script in the web player to receive playback events:

```javascript
window.addEventListener("message", function (event) {
  if (typeof event.data === "string") {
    var messageArea = document.querySelector("#messageArea");
    if (messageArea) {
      messageArea.innerText = event.data;
    }
  }

  try {
    console.log("Message received from the player:", JSON.parse(event.data));
  } catch (error) {
    console.log("Message received from the player:", event.data);
  }
});
```

Event payload shape:

```json
{
  "type": "PLAYER_EVENT",
  "data": {
    "event": "timeupdate|play|pause|ended|seeked",
    "currentTime": 120.5,
    "duration": 7200,
    "progress": 1.6,
    "id": "299534",
    "mediaType": "movie",
    "season": 1,
    "episode": 8,
    "timestamp": 1640995200000
  }
}
```

### Watchlist Module

Personal watchlist management:

- Add/remove movies
- Organization by status
- Cloud sync support
- Local persistence

### Profile Module

User profile and statistics:

- Profile information
- Watch history
- Favorite movies
- Account settings
- Viewing statistics

### Settings Module

Application preferences:

- Theme selection (dark/light)
- Subtitle language
- Video quality defaults
- Playback settings
- Notifications
- Cache management
- Account management

---

## API Integration

The app integrates with **VidKing/Cineby embed URLs** for playback and the API documentation for discovery data:

- Playback base: `https://www.vidking.net`
- Documentation: `https://www.vidking.net/#documentation`
- Default movie color: `ff0000`
- Default autoplay: `true`

### Key Endpoints

```kotlin
// Discovery
GET /api/home/trending        // Trending movies
GET /api/home/popular         // Popular movies
GET /api/home/topRated        // Top rated
GET /api/home/newReleases     // New releases

// Search & Filter
GET /api/search               // Search movies/shows
GET /api/search/suggestions   // Search autocomplete
GET /api/genres               // Genre list
GET /api/genre/{genre}        // Movies by genre

// Content Details
GET /api/movie/{id}           // Movie details
GET /api/movie/{id}/trailer   // Movie trailer
GET /api/movie/{id}/similar   // Similar movies

// TV Shows
GET /api/tv/trending          // Trending TV shows
GET /api/tv/popular           // Popular TV shows
GET /api/tv/{id}/season/{num} // Season details

// Playback embeds
GET /embed/movie/{tmdbId}?color=ff0000&autoPlay=true&nextEpisode=true&episodeSelector=true
GET /embed/tv/{tmdbId}/{season}/{episode}
```

---

## Database Schema

### Entities

**movies** - Cached movie/show data
**favorites** - User's favorite movies
**watch_progress** - Resume watching positions
**search_history** - User search queries
**recently_viewed** - Recently watched content
**user_profile** - User account information
**watch_list** - Movies to watch later

---

## State Management

### MVVM Pattern

Each feature uses ViewModel with StateFlow:

```kotlin
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<HomeUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeUiState>> = _uiState.asStateFlow()

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.getTrendingMovies()
            _uiState.value = when (result) {
                is Result.Success -> UiState.Success(HomeUiState(trending = result.data))
                is Result.Error -> UiState.Error(result.exception.message ?: "Unknown error")
                is Result.Loading -> UiState.Loading
            }
        }
    }
}
```

---

## Performance Optimization

1. **Image Caching** - Coil handles automatic caching
2. **List Pagination** - Paging 3 for efficient scrolling
3. **Database Caching** - Room for offline support
4. **LazyColumn/LazyRow** - Compose lazy layouts
5. **Image Resizing** - Proper dimensions for quality/size tradeoff
6. **Coroutines** - Non-blocking async operations
7. **Flow** - Reactive data flow without blocking

---

## Security Best Practices

1. **HTTPS Only** - All API calls over HTTPS
2. **Token Storage** - Encrypted via DataStore
3. **API Key Management** - Store in BuildConfig (gitignored)
4. **SSL Pinning** - Can be added via OkHttp
5. **Input Validation** - All user inputs validated
6. **Proguard/R8** - Code obfuscation enabled

---

## Testing Strategy

### Unit Testing

```bash
./gradlew test
```

### Instrumentation Testing

```bash
./gradlew connectedAndroidTest
```

---

## Build Variants

### Debug

- Logging enabled
- ProGuard disabled
- Debuggable

### Release

- Optimizations enabled
- ProGuard enabled
- Firebase Crashlytics enabled
- Signing required

---

## Deployment

### Release Build

```bash
./gradlew bundleRelease
```

### Play Store Upload

1. Generate signed APK/AAB
2. Configure version number and code
3. Add release notes
4. Upload to Play Console
5. Review and publish

---

## Troubleshooting

### Common Issues

**Issue: Gradle sync fails**

- Solution: Delete `.gradle` folder, invalidate caches

**Issue: API returns 401**

- Solution: Check Cineby API authentication token

**Issue: Videos won't play**

- Solution: Verify ExoPlayer dependencies, check content format

**Issue: Crashes on startup**

- Solution: Check Firebase configuration, verify permissions in manifest

---

## Future Enhancements

- [ ] Chromecast support
- [ ] Download for offline viewing
- [ ] AI-powered recommendations
- [ ] Voice search
- [ ] Multi-language subtitles
- [ ] Trailer autoplay
- [ ] Android TV mode
- [ ] Shared watchlists
- [ ] Social features
- [ ] Advanced analytics

---

## Contributing

1. Create feature branch: `git checkout -b feature/your-feature`
2. Commit changes: `git commit -am 'Add feature'`
3. Push branch: `git push origin feature/your-feature`
4. Create Pull Request

---

## License

This project is licensed under the MIT License - see LICENSE file for details.

---

## Support

For issues or questions:

- Create an issue on GitHub
- Contact: support@moviestream.app

---

**Happy Streaming!** 🎬🍿
