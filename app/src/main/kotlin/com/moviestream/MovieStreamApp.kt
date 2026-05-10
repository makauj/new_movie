package com.moviestream

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.analytics.FirebaseAnalytics

@HiltAndroidApp
class MovieStreamApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeFirebase()
    }

    private fun initializeFirebase() {
        // Initialize Firebase Crashlytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        
        // Initialize Firebase Analytics
        FirebaseAnalytics.getInstance(this)
    }
}
