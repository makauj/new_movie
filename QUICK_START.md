# MovieStream - Quick Start Guide

Get MovieStream running in 5 minutes!

## Step 1: Install Requirements (5 min)

1. **Install Android Studio**
   - Download from [developer.android.com/studio](https://developer.android.com/studio)
   - Install and open

2. **Select Android SDK 34+**
   - Android Studio → Preferences → Appearance & Behavior → System Settings → Android SDK
   - Check "Android API 34" (or higher)
   - Click "Apply"

3. **Install Java 17+**
   - Android Studio includes JDK
   - Or install from [adoptium.net](https://adoptium.net)

## Step 2: Clone Project (2 min)

```bash
git clone <repository-url>
cd MovieStream
```

Or download as ZIP and extract.

## Step 3: Open in Android Studio (1 min)

1. Android Studio → File → Open
2. Select "MovieStream" folder
3. Wait for Gradle sync to complete (look for "Gradle sync finished" at bottom)

## Step 4: Run the App (2 min)

### Option A: Using Emulator

1. **Create Android Emulator**
   - Android Studio → Tools → Device Manager
   - Click "Create Device"
   - Select device model (e.g., "Pixel 4")
   - Select Android version (API 34+)
   - Click "Finish"

2. **Start Emulator**
   - Device Manager → Right-click device → Launch
   - Wait for emulator to fully boot

3. **Run App**
   - Android Studio → Run → Run 'app'
   - Or press Shift + F10

### Option B: Using Physical Device

1. **Enable Developer Mode**
   - Settings → About Phone → Build Number (tap 7 times)

2. **Enable USB Debugging**
   - Settings → Developer Options → USB Debugging (toggle on)

3. **Connect via USB**
   - Plug device into computer
   - Grant USB permission on device
   - Android Studio should recognize device

4. **Run App**
   - Android Studio → Run → Run 'app'

## What You Should See

✅ App launches  
✅ MovieStream logo appears  
✅ Home screen with movies displayed  
✅ Smooth animations and scrolling

## Next Steps

### 1. Explore Features

- **Home Screen** - Browse trending/popular movies
- **Search** - Find movies by name
- **Movie Details** - View ratings, cast, trailers
- **Player** - Play video content
- **Watchlist** - Save movies for later
- **Settings** - Customize experience

### 2. Customize

Edit these files to personalize:

- **Colors** → `app/src/main/kotlin/com/moviestream/ui/theme/Theme.kt`
- **Text** → `app/src/main/res/values/strings.xml`
- **Animations** → Individual Composable files

### 3. Build for Release

```bash
# Create release build
./gradlew bundleRelease

# Output: app/release/app-release.aab
```

## Common Issues & Quick Fixes

| Issue               | Fix                                                        |
| ------------------- | ---------------------------------------------------------- |
| Gradle sync fails   | Close Android Studio, delete `.gradle` folder, reopen      |
| "API not found"     | Make sure Android SDK 34+ is installed                     |
| Device not detected | Install USB drivers, enable USB debugging                  |
| Build takes forever | First build is slow; subsequent builds are faster          |
| Crashes on startup  | Check `Logcat` tab for errors; try `./gradlew clean build` |

## Project Structure Quick Reference

```
MovieStream/
├── app/              ← Main app code
├── core/             ← Shared utilities
│   ├── common/       ← Models & interfaces
│   ├── network/      ← API calls
│   ├── database/     ← Offline storage
│   └── datastore/    ← User settings
├── feature/          ← App features
│   ├── home/         ← Home screen
│   ├── search/       ← Search feature
│   ├── player/       ← Video player
│   └── ... (more features)
└── README.md         ← Full documentation
```

## Key Technologies

| Technology          | Purpose                |
| ------------------- | ---------------------- |
| **Kotlin**          | Programming language   |
| **Jetpack Compose** | Build UI               |
| **Retrofit**        | Download data from API |
| **Room**            | Save data locally      |
| **ExoPlayer**       | Play videos            |
| **Hilt**            | Dependency injection   |

## Development Tips

### Hot Reload Code Changes

```bash
# Method 1: Use "Apply Code Changes"
Android Studio → Modify code → Ctrl+Alt+F5

# Method 2: Full rebuild
Android Studio → Build → Rebuild Project
```

### View Logs

```bash
# Android Studio → View → Tool Windows → Logcat

# Or terminal:
adb logcat | grep moviestream
```

### Take Screenshots

```bash
# In Android Studio:
# Tools → Device File Explorer → Find screenshot in DCIM/Screenshots

# Or terminal:
adb shell screencap -p > screenshot.png
```

## Deploying to Play Store

1. Create Google Play Developer account ($25 one-time)
2. Generate signed APK/AAB
3. Test with internal testers first
4. Submit for review
5. Publish!

See [IMPLEMENTATION_GUIDE.md](IMPLEMENTATION_GUIDE.md) for detailed steps.

## Need More Help?

📚 **Documentation**

- [README.md](README.md) - Full project overview
- [IMPLEMENTATION_GUIDE.md](IMPLEMENTATION_GUIDE.md) - Detailed implementation
- [BUILD_INSTRUCTIONS.md](BUILD_INSTRUCTIONS.md) - All build commands

🔗 **Resources**

- [Android Developers](https://developer.android.com)
- [Jetpack Compose Tutorial](https://developer.android.com/develop/ui/compose/tutorial)
- [Kotlin Documentation](https://kotlinlang.org/docs)

💬 **Community**

- Stack Overflow: [android] or [kotlin] tags
- Reddit: [r/androiddev](https://www.reddit.com/r/androiddev)
- GitHub Issues: Create an issue in repo

---

**Congratulations!** You now have a fully functional movie streaming app! 🎬🚀

Happy coding! If you get stuck, refer to the troubleshooting section or check the detailed guides above.
