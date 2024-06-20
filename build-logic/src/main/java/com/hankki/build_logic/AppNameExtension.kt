package com.hankki.build_logic

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.hankki.$name"
    }
}