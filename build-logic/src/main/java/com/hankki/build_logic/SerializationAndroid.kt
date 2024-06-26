package com.hankki.build_logic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureSerializationAndroid() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.serialization")
    }

    val libs = extensions.libs
    dependencies {
        add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
    }
}

internal class SerializationAndroidPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureSerializationAndroid()
        }
    }
}
