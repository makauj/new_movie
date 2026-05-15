pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "MovieStream"

include(":app")
include(":core:common")
include(":core:network")
include(":core:database")
include(":core:datastore")
include(":feature:authentication")
include(":feature:home")
include(":feature:search")
include(":feature:details")
include(":feature:player")
include(":feature:watchlist")
include(":feature:profile")
include(":feature:settings")
