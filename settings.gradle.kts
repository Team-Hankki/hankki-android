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

        // naver
        maven {
            url = uri("https://repository.map.naver.com/archive/maven")
        }

        //kakao
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "hankkijogbo"
include(":app")

// core
include(
    ":core:common",
    ":core:datastore",
    ":core:designsystem",
    ":core:network",
    ":core:navigation"
)

// data
include(
    ":data:token",
    ":data:login",
    ":data:reissuetoken",
    ":data:home",
    ":data:report",
    ":data:storedetail",
    ":data:my",
    ":data:universityselection"
)

// domain
include(
    ":domain:my",
    ":domain:universityselection",
    ":domain:report",
    ":domain:token",
    ":domain:login",
    ":domain:reissuetoken",
    ":domain:home",
    ":domain:storedetail"
)

// feature
include(
    ":feature:main",
    ":feature:home",
    ":feature:report",
    ":feature:my",
    ":feature:login",
    ":feature:universityselection",
    ":feature:storedetail"
)

// baselineprofile
include(":baselineprofile")
