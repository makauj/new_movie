# MovieStream - Architecture & Design Document

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Clean Architecture](#clean-architecture)
3. [MVVM Pattern](#mvvm-pattern)
4. [Dependency Injection](#dependency-injection)
5. [Data Flow](#data-flow)
6. [Error Handling](#error-handling)
7. [Navigation](#navigation)
8. [Testing Strategy](#testing-strategy)

---

## Architecture Overview

MovieStream uses a layered, modular architecture that combines:

- **Clean Architecture** - Three layers: Presentation, Domain, Data
- **MVVM Pattern** - Reactive UI with ViewModels
- **Dependency Injection** - Hilt for loose coupling
- **Repository Pattern** - Abstraction over data sources
- **Reactive Programming** - Coroutines & Flow

### High-Level Architecture Diagram

```
┌─────────────────────────────────────────────┐
│         PRESENTATION LAYER                  │
│  (UI, ViewModels, Navigation)              │
├─────────────────────────────────────────────┤
│         DOMAIN LAYER                        │
│  (Use Cases, Entities, Repository Interfaces)
├─────────────────────────────────────────────┤
│         DATA LAYER                          │
│  (API, Database, Repository Implementations)
└─────────────────────────────────────────────┘
```

---

## Clean Architecture

### Layer Definitions

#### 1. **Presentation Layer** (UI)

**Location:** `feature/*`

**Responsibilities:**

- Display UI to user
- Capture user input
- Manage UI state
- Navigate between screens

**Components:**

- `Screen` composables (Jetpack Compose)
- `ViewModel` (lifecycle-aware)
- `UiState` (sealed classes)
- `Events` (user actions)

**Example:**

```kotlin
// HomeScreen.kt
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHomeData()
    }

    when (uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> HomeContent((uiState as UiState.Success).data)
        is UiState.Error -> ErrorScreen((uiState as UiState.Error).message)
        is UiState.Idle -> EmptyScreen()
    }
}
```

#### 2. **Domain Layer** (Business Logic)

**Location:** `core/common`

**Responsibilities:**

- Define business rules
- Repository interfaces
- Use cases/interactors
- Domain models

**Components:**

- Domain Models (`Movie`, `MovieDetails`, `User`)
- Repository Interfaces
- Use Cases (optional)
- Exceptions

**Example:**

```kotlin
// MovieRepository.kt (interface)
interface MovieRepository {
    suspend fun getTrendingMovies(page: Int = 1): Result<List<Movie>>
    suspend fun getMovieDetails(movieId: String): Result<MovieDetails>
    suspend fun searchMovies(query: String): Result<List<Movie>>
}
```

#### 3. **Data Layer** (Data Sources)

**Location:** `core/network`, `core/database`

**Responsibilities:**

- Fetch data from API
- Store/retrieve from database
- Cache management
- Data transformation

**Components:**

- Retrofit API Service
- Room Database
- Repository Implementations
- DTOs (Data Transfer Objects)
- Mappers

**Example:**

```kotlin
// MovieRepositoryImpl.kt
class MovieRepositoryImpl @Inject constructor(
    private val api: CinebytApi,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getTrendingMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = api.getTrendingMovies(page = page)
            // Cache in database
            movieDao.insertMovies(response.movies.map { it.toEntity() })
            Result.Success(response.movies.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
```

### Layer Boundaries

```
Presentation
    ↓ (depends on)
Domain
    ↓ (depends on)
Data

// BUT:
Data DOES NOT depend on Presentation
Presentation DOES NOT depend on Data (only through Domain)
```

---

## MVVM Pattern

### Model

**Represents state and data**

```kotlin
data class HomeUiState(
    val trendingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val recommendations: List<Movie> = emptyList()
)
```

### View

**Displays state and captures user input**

```kotlin
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        TrendingCarousel(
            movies = (uiState as? UiState.Success)?.data?.trendingMovies ?: emptyList(),
            onMovieClick = { movieId -> viewModel.onMovieSelected(movieId) }
        )
    }
}
```

### ViewModel

**Manages state and business logic**

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<HomeUiState>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeUiState>> = _uiState.asStateFlow()

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val trendingResult = repository.getTrendingMovies()
            val popularResult = repository.getPopularMovies()

            _uiState.value = if (trendingResult.isSuccess && popularResult.isSuccess) {
                UiState.Success(HomeUiState(
                    trendingMovies = trendingResult.getOrNull() ?: emptyList(),
                    popularMovies = popularResult.getOrNull() ?: emptyList()
                ))
            } else {
                UiState.Error("Failed to load content")
            }
        }
    }
}
```

### State Sealed Classes

```kotlin
sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}
```

---

## Dependency Injection

### Hilt Setup

**Application class:**

```kotlin
@HiltAndroidApp
class MovieStreamApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase, etc.
    }
}
```

### Module Setup

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCinebytApi(retrofit: Retrofit): CinebytApi {
        return retrofit.create(CinebytApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}
```

### Injection in Components

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository  // Injected automatically
) : ViewModel()

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()  // Provided by Hilt
)
```

---

## Data Flow

### Unidirectional Data Flow

```
User Action
    ↓
ViewModel.function()
    ↓
Repository.function()
    ↓
API / Database
    ↓
Transform (DTO → Domain Model)
    ↓
Update Repository/Cache
    ↓
Return Result
    ↓
ViewModel.uiState.value = Success(data)
    ↓
Recompose Screen with new state
```

### Example Flow: Load Trending Movies

```kotlin
// 1. User launches screen
HomeScreen()

// 2. LaunchedEffect triggers loading
LaunchedEffect(Unit) {
    viewModel.loadHomeData()
}

// 3. ViewModel calls repository
suspend fun loadHomeData() {
    val result = repository.getTrendingMovies()
}

// 4. Repository calls API
suspend fun getTrendingMovies(): Result<List<Movie>> {
    val response = api.getTrendingMovies()  // Network call
    movieDao.insertMovies(...)              // Cache it
    return Result.Success(response.toDomain())
}

// 5. Transform DTO to Domain
fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    // ...
)

// 6. Update ViewModel state
_uiState.value = UiState.Success(HomeUiState(
    trendingMovies = result.data
))

// 7. Screen recomposes with new state
val uiState by viewModel.uiState.collectAsState()
if (uiState is UiState.Success) {
    // Show movies
}
```

---

## Error Handling

### Exception Hierarchy

```kotlin
sealed class AppException : Exception() {
    data class NetworkException(override val message: String) : AppException()
    data class ParseException(override val message: String) : AppException()
    data class ServerException(val code: Int) : AppException()
    data class NotAuthorizedException : AppException()
    data class UnknownException(val throwable: Throwable) : AppException()
}
```

### Error Handling in Repository

```kotlin
override suspend fun getTrendingMovies(): Result<List<Movie>> {
    return try {
        val response = api.getTrendingMovies()
        Result.Success(response.movies.map { it.toDomain() })
    } catch (e: HttpException) {
        when (e.code()) {
            401 -> Result.Error(NotAuthorizedException())
            500 -> Result.Error(ServerException(e.code()))
            else -> Result.Error(NetworkException(e.message ?: "Unknown error"))
        }
    } catch (e: IOException) {
        Result.Error(NetworkException("No internet connection"))
    } catch (e: Exception) {
        Result.Error(UnknownException(e))
    }
}
```

### Error Handling in ViewModel

```kotlin
fun loadHomeData() {
    viewModelScope.launch {
        when (val result = repository.getTrendingMovies()) {
            is Result.Success -> {
                _uiState.value = UiState.Success(HomeUiState(trending = result.data))
            }
            is Result.Error -> {
                val errorMessage = when (result.exception) {
                    is NotAuthorizedException -> "Please log in"
                    is ServerException -> "Server error"
                    is NetworkException -> "No internet connection"
                    else -> "Unknown error"
                }
                _uiState.value = UiState.Error(errorMessage)
            }
            is Result.Loading -> {
                _uiState.value = UiState.Loading
            }
        }
    }
}
```

### Error Display in UI

```kotlin
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            val data = (uiState as UiState.Success<HomeUiState>).data
            HomeContent(data)
        }
        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            ErrorScreen(
                message = message,
                onRetry = { viewModel.loadHomeData() }
            )
        }
        is UiState.Idle -> EmptyScreen()
    }
}
```

---

## Navigation

### Navigation Structure

```
ROOT GRAPH
├── AUTH GRAPH
│   ├── Splash Screen
│   ├── Login Screen
│   ├── Sign Up Screen
│   └── Forgot Password Screen
└── MAIN GRAPH
    ├── Home Screen
    ├── Search Screen
    ├── Details Screen
    ├── Player Screen
    ├── Watchlist Screen
    ├── Profile Screen
    └── Settings Screen
```

### Navigation Implementation

```kotlin
sealed class Destination(val route: String) {
    data object Home : Destination("home")
    data object Search : Destination("search")
    data class Details(val movieId: String) : Destination("details/{id}")
    data class Player(val movieId: String) : Destination("player/{id}")
}

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTH
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}

private fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(route = Graph.MAIN, startDestination = MainScreen.HOME) {
        composable(route = MainScreen.HOME) {
            HomeScreen(
                onNavigateToDetails = { movieId ->
                    navController.navigate("details/$movieId")
                }
            )
        }
        composable(route = MainScreen.DETAILS) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString("id")
            DetailsScreen(movieId = movieId)
        }
    }
}
```

---

## Testing Strategy

### Unit Tests

**Test ViewModels:**

```kotlin
@HiltAndroidTest
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private val mockRepository = mockk<MovieRepository>()

    @Before
    fun setup() {
        viewModel = HomeViewModel(mockRepository)
    }

    @Test
    fun testLoadHomeData_Success() = runTest {
        val mockMovies = listOf(Movie(id = "1", title = "Test"))
        coEvery { mockRepository.getTrendingMovies() } returns Result.Success(mockMovies)

        viewModel.loadHomeData()

        val state = viewModel.uiState.value
        assert(state is UiState.Success)
    }
}
```

### Integration Tests

**Test API calls:**

```kotlin
@RunWith(AndroidJUnit4::class)
class MovieRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: MovieRepository

    @Test
    fun testGetTrendingMovies() = runBlocking {
        val result = repository.getTrendingMovies()
        assert(result.isSuccess())
    }
}
```

---

## Principles & Best Practices

### SOLID Principles

- **S**ingle Responsibility - Each class has one reason to change
- **O**pen/Closed - Open for extension, closed for modification
- **L**iskov Substitution - Subtypes must be substitutable
- **I**nterface Segregation - Clients depend on specific interfaces
- **D**ependency Inversion - Depend on abstractions, not concretions

### Android Lifecycle Considerations

```kotlin
// Lifecycle-aware collection
lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.collect { state ->
            // Update UI only when STARTED
        }
    }
}
```

### Reactive Programming

- Use **Flow** for reactive streams
- Use **StateFlow** for state management
- Use **Coroutines** for async operations
- Avoid mutable state in Compose

---

## Performance Considerations

### Memory Management

```kotlin
// Good - Use lazy initialization
private val _uiState = lazy { MutableStateFlow<UiState<T>>(UiState.Idle) }

// Bad - Heavy initialization in init block
init {
    loadAllDataAtOnce()
}
```

### Database Query Optimization

```kotlin
// Good - Pagination
suspend fun getMovies(page: Int, pageSize: Int): List<Movie>

// Bad - Load all at once
suspend fun getAllMovies(): List<Movie>
```

### Image Loading Optimization

```kotlin
// Good - Coil handles caching automatically
AsyncImage(
    model = "https://...",
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier.size(200.dp)
)
```

---

## Security Best Practices

1. **Never expose API keys in code**

   ```kotlin
   // Good
   val apiKey = BuildConfig.API_KEY

   // Bad
   val apiKey = "hardcoded-key-123"
   ```

2. **Use HTTPS only**
3. **Validate user input**
4. **Encrypt sensitive data**
5. **Implement token refresh**

---

This architecture provides a scalable, maintainable, and testable foundation for the MovieStream app.
