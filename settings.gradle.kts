enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "hankkijogbo"
include(":app")

// core
include(
    ":core:common",
    ":core:datastore",
    ":core:designsystem",
    ":core:network"
)

// data
include(
    ":data:dummy"
)

// domain
include(
    ":domain:dummy"
)

// feature
include(
    ":feature:dummy",
    ":feature:main"
)