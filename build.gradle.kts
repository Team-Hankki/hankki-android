@file:Suppress("DSL_SCOPE_VIOLATION")

buildscript {
    repositories {
        google()
        mavenCentral()
        maven{
            url = uri("https://repository.map.naver.com/archive/maven")
        }
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.android.library) apply false
}

apply {
    from("gradle/projectDependencyGraph.gradle")
}
