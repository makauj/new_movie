# ProGuard rules for MovieStream app

# Keep all classes in the app package
-keep class com.moviestream.** { *; }

# Keep Firebase classes
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Keep Retrofit/Okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class okio.** { *; }

# Keep Retrofit annotations
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

# Keep Kotlin metadata
-keepclassmembers class ** {
    *** Companion;
}

# Keep serialization classes
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class kotlinx.serialization.** { *; }

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class hilt_aggregated_deps.** { *; }

# Keep Room database classes
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }

# Keep Data classes
-keepclassmembers class * {
    *** component1(...);
    *** component2(...);
    *** component3(...);
    *** component4(...);
    *** component5(...);
    *** component6(...);
    *** component7(...);
    *** copy(...);
}

# Keep enum values
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Preserve line numbers for crash reporting
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Remove logging in production
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
