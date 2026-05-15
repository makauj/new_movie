// Top-level build.gradle.kts
plugins {
    id("com.android.application") version "9.2.1" apply false
    id("com.android.library") version "9.2.1" apply false
    kotlin("android") version "2.2.10" apply false
    kotlin("jvm") version "1.9.23" apply false
    kotlin("kapt") version "1.9.23" apply false
    kotlin("plugin.serialization") version "1.9.23" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
