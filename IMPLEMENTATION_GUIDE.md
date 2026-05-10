# MovieStream - Implementation Guide

This guide provides step-by-step instructions for implementing the complete MovieStream Android application.

## Table of Contents

1. [Project Setup](#project-setup)
2. [Core Modules Implementation](#core-modules-implementation)
3. [Feature Modules Implementation](#feature-modules-implementation)
4. [API Integration](#api-integration)
5. [Testing](#testing)
6. [Deployment](#deployment)

---

## Project Setup

### Step 1: Initialize Android Studio Project

```bash
# Create new project structure
mkdir -p src/main/kotlin/com/moviestream
mkdir -p src/main/res/{values,xml,layout}
mkdir -p core/{common,network,database,datastore}/src/main
mkdir -p feature/{authentication,home,search,details,player,watchlist,profile,settings}/src/main
```

### Step 2: Create Root build.gradle.kts

Already created in `build.gradle.kts` with plugin management.

### Step 3: Configure Settings

Already created in `settings.gradle.kts` with all module references.

### Step 4: App Module Configuration

Already created in `app/build.gradle.kts` with all dependencies.

---

## Core Modules Implementation

### Core: Common Module

**Purpose:** Shared utilities, models, and interfaces

**Files Created:**

- `Result.kt` - Sealed class for handling API results
- `Models.kt` - Domain models (Movie, MovieDetails, etc.)
- `Utils.kt` - Utility functions for formatting
- `MovieRepository.kt` - Repository interface

**Next Steps:**

1. Create use case classes for business logic
2. Add mappers for DTO → Domain model conversion
3. Create additional domain models as needed

### Core: Network Module

**Purpose:** API integration with Cineby

**Files Created:**

- `CinebytApi.kt` - Retrofit service interface
- `MovieDto.kt` - API response models
- `MovieRepositoryImpl.kt` - Repository implementation
- `NetworkModule.kt` - Hilt dependency injection setup

**Configuration:**

```kotlin
// Base URL: https://api.cineby.sc/
// Add API key via interceptor if required
```

**Testing:**

```bash
# Test network connectivity
./gradlew test
```

### Core: Database Module

**Purpose:** Local data persistence

**Files Created:**

- `MovieStreamDatabase.kt` - Room database class
- `Entities.kt` - All database entities
- `Daos.kt` - Database access objects
- `DatabaseModule.kt` - Hilt setup

**Usage:**

```kotlin
// Inject into repository
class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val favoriteDao: FavoriteDao
)
```

### Core: DataStore Module

**Purpose:** User preferences and settings

**Files Created:**

- `UserPreferencesRepository.kt` - Encrypted preferences

**Usage:**

```kotlin
// Inject and observe preferences
val userPreferences = userPreferencesRepository.userPreferences
userPreferences.collect { prefs ->
    // Use preferences
}
```

---

## Feature Modules Implementation

### Feature: Authentication

**Screens:**

1. **Login Screen**
   - Email/password input
   - Forgot password link
   - Sign up navigation
   - Guest mode option

2. **Sign Up Screen**
   - Email, password, confirm password
   - Validation
   - Terms acceptance

3. **Splash Screen**
   - App logo/branding
   - Session check
   - Auto-login if token valid

**ViewModels to Create:**

- `AuthViewModel` - Login/signup logic
- `SplashViewModel` - Session validation

**Firebase Integration:**

```kotlin
private val firebaseAuth = FirebaseAuth.getInstance()

fun login(email: String, password: String) {
    firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnSuccessListener { result ->
            // Save user info
        }
}
```

### Feature: Home

**Screens:**

1. **Home Screen (Main)**
   - Hero banner carousel (featured movies)
   - Horizontal scrolling sections:
     - Trending
     - Popular
     - Top Rated
     - Continue Watching
     - Recommendations

**ViewModels:**

- `HomeViewModel` - Load home data

**Implementation:**

```kotlin
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<HomeUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeUiState>> = _uiState

    fun loadHomeData() {
        viewModelScope.launch {
            val trending = repository.getTrendingMovies()
            val popular = repository.getPopularMovies()
            // Combine results
        }
    }
}
```

### Feature: Search

**Screens:**

1. **Search Screen**
   - Search bar with suggestions
   - Search history
   - Filter options
   - Results grid

**ViewModels:**

- `SearchViewModel` - Search and filter logic

**Features:**

- Real-time search with debounce
- Cached results
- Filter by genre, year, rating

### Feature: Details

**Screens:**

1. **Movie Details Screen**
   - Movie poster & backdrop
   - Title, rating, genres
   - Description, cast, crew
   - Trailer
   - Similar movies
   - Add to watchlist/favorites

**ViewModels:**

- `DetailsViewModel` - Load movie details

**Features:**

- Expandable description
- Horizontal cast carousel
- Trailer playback in overlay

### Feature: Player

**Screens:**

1. **Video Player Screen**
   - ExoPlayer integration
   - Full screen support
   - Controls overlay
   - Subtitle/quality selection

**Components:**

- `MoviePlayerController` - Player control logic
- `PlayerViewModel` - State management

**Features:**

- Adaptive streaming
- Quality selection (720p, 1080p, etc.)
- Subtitle support
- Playback speed (0.5x, 1x, 1.5x, 2x)
- Progress saving
- Picture-in-Picture

### Feature: Watchlist

**Screens:**

1. **Watchlist Screen**
   - List of saved movies
   - Remove option
   - Sort/filter options

**ViewModels:**

- `WatchlistViewModel` - Manage watchlist

### Feature: Profile

**Screens:**

1. **Profile Screen**
   - User info
   - Watch history
   - Statistics
   - Settings quick access

**ViewModels:**

- `ProfileViewModel` - User data

### Feature: Settings

**Screens:**

1. **Settings Screen**
   - Display settings
   - Playback preferences
   - Notifications
   - Cache management
   - About & Help

---

## API Integration

### Cineby API Setup

1. **Review Documentation**
   - https://www.vidking.net/#documentation

2. **Create API Service**

   ```kotlin
   interface CinebytApi {
       @GET("api/home/trending")
       suspend fun getTrendingMovies(): MovieListResponse
       // ... other endpoints
   }
   ```

3. **Implement Repository**
   - Convert DTOs to domain models
   - Handle errors gracefully
   - Cache responses in database

### Error Handling

```kotlin
sealed class ApiException : Exception() {
    data class NetworkException(override val message: String) : ApiException()
    data class ParsingException(override val message: String) : ApiException()
    data class ServerException(val code: Int) : ApiException()
    data class UnauthorizedException() : ApiException()
}
```

---

## UI Components to Create

### Common Composables

1. **MovieCard** - Displaying single movie
2. **MovieCarousel** - Horizontal scrolling list
3. **HeroImage** - Featured movie banner
4. **LoadingScreen** - Loading state
5. **ErrorScreen** - Error state
6. **EmptyState** - No content state
7. **BottomSheet** - Options menu
8. **BottomNavigation** - App navigation

### Theme Components

Already created:

- `Theme.kt` - Color scheme
- `Type.kt` - Typography

**Add:**

- `Dimensions.kt` - Spacing values
- `Icons.kt` - App icons

---

## Testing Strategy

### Unit Tests

```bash
# Create test files
src/test/kotlin/com/moviestream/

# Test ViewModels
src/test/kotlin/com/moviestream/feature/home/HomeViewModelTest.kt

# Test Repository
src/test/kotlin/com/moviestream/core/network/MovieRepositoryTest.kt
```

### Integration Tests

```bash
# Instrumented tests
src/androidTest/kotlin/com/moviestream/

# Test navigation
src/androidTest/kotlin/com/moviestream/NavigationTest.kt

# Test API calls
src/androidTest/kotlin/com/moviestream/ApiIntegrationTest.kt
```

### Run Tests

```bash
# Unit tests
./gradlew test

# Integration tests
./gradlew connectedAndroidTest

# Test with coverage
./gradlew testDebugCoverage
```

---

## Build and Deploy

### Debug Build

```bash
# Build debug APK
./gradlew assembleDebug

# Install on device
./gradlew installDebug

# Run with logging
./gradlew run --info
```

### Release Build

```bash
# Create signing key
keytool -genkey -v -keystore moviestream-release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 -alias moviestream

# Build release APK
./gradlew assembleRelease

# Build App Bundle (for Play Store)
./gradlew bundleRelease
```

### Firebase Setup

1. Create Firebase project
2. Download `google-services.json`
3. Place in `app/` directory
4. Enable required services:
   - Authentication
   - Realtime Database (for watchlist sync)
   - Cloud Messaging (for notifications)
   - Crashlytics

---

## File Structure Checklist

### Required Files Created ✓

```
app/
├── build.gradle.kts ✓
├── proguard-rules.pro ✓
├── src/main/
│   ├── AndroidManifest.xml ✓
│   ├── kotlin/com/moviestream/
│   │   ├── MainActivity.kt ✓
│   │   ├── MovieStreamApp.kt ✓
│   │   ├── navigation/RootNavGraph.kt ✓
│   │   └── ui/theme/
│   │       ├── Theme.kt ✓
│   │       └── Type.kt ✓
│   └── res/
│       ├── values/strings.xml ✓
│       └── xml/data_extraction_rules.xml ✓

core/
├── common/
│   ├── build.gradle.kts ✓
│   └── src/main/kotlin/com/moviestream/core/common/
│       ├── Result.kt ✓
│       ├── Utils.kt ✓
│       ├── model/Models.kt ✓
│       └── repository/MovieRepository.kt ✓
├── network/
│   ├── build.gradle.kts ✓
│   └── src/main/kotlin/com/moviestream/core/network/
│       ├── api/CinebytApi.kt ✓
│       ├── model/MovieDto.kt ✓
│       ├── repository/MovieRepositoryImpl.kt ✓
│       └── di/
│           ├── NetworkModule.kt ✓
│           └── RepositoryModule.kt ✓
├── database/
│   ├── build.gradle.kts ✓
│   └── src/main/kotlin/com/moviestream/core/database/
│       ├── MovieStreamDatabase.kt ✓
│       ├── entity/Entities.kt ✓
│       ├── dao/Daos.kt ✓
│       └── di/DatabaseModule.kt ✓
└── datastore/
    ├── build.gradle.kts ✓
    └── src/main/kotlin/com/moviestream/core/datastore/
        └── UserPreferencesRepository.kt ✓

feature/
├── authentication/build.gradle.kts ✓
├── home/build.gradle.kts ✓
├── search/build.gradle.kts ✓
├── details/build.gradle.kts ✓
├── player/build.gradle.kts ✓
├── watchlist/build.gradle.kts ✓
├── profile/build.gradle.kts ✓
└── settings/build.gradle.kts ✓

Root Files:
├── build.gradle.kts ✓
├── settings.gradle.kts ✓
├── gradle.properties ✓
├── .gitignore ✓
└── README.md ✓
```

---

## Next Steps

1. **Create Screen Composables**
   - Implement UI for each feature module
   - Use Material 3 design system
   - Add animations and transitions

2. **Implement ViewModels**
   - Load data from repositories
   - Handle UI state
   - Manage user interactions

3. **Add Tests**
   - Unit tests for ViewModels
   - Integration tests for API
   - UI tests for screens

4. **Firebase Integration**
   - Authentication setup
   - Analytics integration
   - Crashlytics setup

5. **Performance Optimization**
   - Profile app with Profiler
   - Optimize memory usage
   - Improve startup time

6. **Release Preparation**
   - Code obfuscation
   - Version management
   - App signing

---

## Resources

- [Android Developers](https://developer.android.com)
- [Jetpack Compose Docs](https://developer.android.com/develop/ui/compose)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [MVVM Pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
- [Cineby API](https://www.vidking.net/#documentation)

---

**Happy Coding!** 🚀
