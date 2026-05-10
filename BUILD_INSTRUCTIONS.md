# MovieStream - Build Instructions

Quick reference for building and running the MovieStream Android app.

## Prerequisites

- Android Studio Flamingo or later
- Android SDK 34+
- Android NDK (if building native modules)
- Git
- Java 17+

## Quick Start

### Clone Repository

```bash
git clone <repository-url>
cd MovieStream
```

### Open in Android Studio

```bash
# From command line
open -a "Android Studio" .

# Or open Android Studio and select: File > Open > MovieStream
```

### Sync Gradle

```bash
# Sync Gradle files
./gradlew sync

# Or in Android Studio: File > Sync Now
```

## Build Commands

### Clean Build

```bash
./gradlew clean
```

### Debug Build

```bash
# Build debug APK
./gradlew assembleDebug

# Build and install on connected device
./gradlew installDebug

# Build and run immediately
./gradlew installDebugAndRun
```

### Release Build

First, create a keystore file:

```bash
keytool -genkey -v -keystore moviestream-release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias moviestream \
  -storepass <password> \
  -keypass <password>
```

Then build:

```bash
# Build release APK
./gradlew assembleRelease

# Build App Bundle (for Play Store)
./gradlew bundleRelease
```

### Build with Specific Flavor

```bash
./gradlew assembleDebug
./gradlew assembleRelease
```

## Run Commands

### Run on Emulator

```bash
# Launch app on connected device/emulator
./gradlew installDebug

# In Android Studio: Run > Run 'app'
```

### Run Specific Tests

```bash
# Run all tests
./gradlew test

# Run tests for specific module
./gradlew core:network:test

# Run instrumentation tests
./gradlew connectedAndroidTest

# Run with coverage
./gradlew testDebugCoverage
```

## Development Workflow

### Start Development

```bash
# 1. Sync Gradle
./gradlew sync

# 2. Clean build
./gradlew clean

# 3. Build debug APK
./gradlew assembleDebug

# 4. Run on device
./gradlew installDebugAndRun
```

### Check for Errors

```bash
# Lint check
./gradlew lint

# Lint specific module
./gradlew app:lint
```

### Build Performance

```bash
# Build with profiler
./gradlew assembleDebug --profile

# View profile at: build/reports/profile/
```

## Advanced Commands

### Enable/Disable Modules

Edit `settings.gradle.kts`:

```kotlin
// Comment out to disable module
include(":feature:authentication")
```

### Update Dependencies

```bash
# Check for dependency updates
./gradlew dependencyUpdates

# Update to latest versions
./gradlew upgradeAll
```

### Generate Documentation

```bash
# Generate Javadoc
./gradlew javadoc

# Generate Dokka documentation
./gradlew dokkaHtml
```

### Memory and Performance

```bash
# Increase heap size for Gradle
export GRADLE_OPTS="-Xmx2g"

# Run build with parallel execution
./gradlew build --parallel

# Use build cache
./gradlew build --build-cache
```

## Troubleshooting

### Gradle Sync Issues

```bash
# Clean gradle cache
rm -rf ~/.gradle/caches

# Sync again
./gradlew --refresh-dependencies sync
```

### Build Failures

```bash
# Clean and rebuild
./gradlew clean build

# Force update dependencies
./gradlew --refresh-dependencies build

# Verbose output for debugging
./gradlew build --info
```

### Device Connection Issues

```bash
# List connected devices
adb devices

# Restart ADB
adb kill-server
adb start-server

# Connect to device
adb connect <device-ip>:5555
```

### Firebase Issues

```bash
# Ensure google-services.json is in app/ directory
ls app/google-services.json

# Clean Firebase gradle plugin cache
rm -rf .gradle
./gradlew clean build
```

## Environment Configuration

### Create Local Properties

Create `local.properties` (gitignored):

```properties
sdk.dir=/Users/your-user/Library/Android/sdk
ndk.dir=/Users/your-user/Library/Android/ndk
```

### Set API Keys

Create `gradle.properties` (gitignored):

```properties
CINEBY_API_KEY=your_api_key_here
FIREBASE_PROJECT_ID=your_project_id
```

Reference in code:

```kotlin
val apiKey = BuildConfig.CINEBY_API_KEY
```

## CI/CD Integration

### GitHub Actions

Create `.github/workflows/build.yml`:

```yaml
name: Build

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: "17"
      - name: Build
        run: ./gradlew build
      - name: Test
        run: ./gradlew test
```

## Useful Gradle Tasks

```bash
# List all tasks
./gradlew tasks

# Show dependency tree
./gradlew dependencies

# Show dependency conflicts
./gradlew dependencyInsight

# Generate AAB for signing
./gradlew bundleRelease

# Validate Android resources
./gradlew validateSigningDebug

# Build app size report
./gradlew bundleRelease --build-analyzer
```

## Performance Tips

```bash
# 1. Use daemon
export GRADLE_OPTS="-Xmx2048m"

# 2. Enable parallel builds
# In gradle.properties:
org.gradle.parallel=true

# 3. Enable build cache
# In gradle.properties:
org.gradle.caching=true

# 4. Use Gradle 8.0+
./gradlew wrapper --gradle-version=8.0

# 5. Profile your build
./gradlew build --profile
```

## Release Checklist

- [ ] Update version number in build.gradle.kts
- [ ] Update CHANGELOG
- [ ] Test on multiple devices
- [ ] Run all tests: `./gradlew test`
- [ ] Check lint: `./gradlew lint`
- [ ] Build release APK: `./gradlew assembleRelease`
- [ ] Sign APK
- [ ] Create release notes
- [ ] Upload to Play Store
- [ ] Tag release in Git

## Common Issues & Solutions

### Issue: Compilation fails with dependency error

```bash
# Solution: Update Gradle wrapper
./gradlew wrapper --gradle-version=latest
```

### Issue: ExoPlayer or Media3 not found

```bash
# Solution: Add JitPack repository in build.gradle.kts
repositories {
    maven { url = uri("https://jitpack.io") }
}
```

### Issue: Firebase services not initializing

```bash
# Solution: Add google-services.json to app/ directory
cp downloads/google-services.json app/
```

### Issue: Compose compilation error

```bash
# Solution: Update Compose compiler version
kotlinCompilerExtensionVersion = "1.5.8"
```

## Documentation

- [Project README](README.md)
- [Implementation Guide](IMPLEMENTATION_GUIDE.md)
- [Android Docs](https://developer.android.com)
- [Jetpack Compose](https://developer.android.com/develop/ui/compose)

---

**Need Help?** Check the troubleshooting section or create an issue on GitHub.
