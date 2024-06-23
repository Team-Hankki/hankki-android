package com.hankki.build_logic

import org.gradle.api.Project

internal fun Project.configureKotestAndroid() {
    configureKotest()
    configureJUnitAndroid()
}

@Suppress("UnstableApiUsage")
internal fun Project.configureJUnitAndroid() {
    androidExtension.apply {
        testOptions {
            unitTests.all { it.useJUnitPlatform() }
        }
    }
}
