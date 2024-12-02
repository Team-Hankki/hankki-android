package com.hankki.build_logic

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureComposeAndroid() {
    with(plugins) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }

    extensions.getByType<BaseExtension>().apply {
        buildFeatures.apply {
            compose = true
        }
    }

    val libs = extensions.libs
    androidExtension.apply {
        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))

            add("implementation", libs.findLibrary("androidx.compose.material3").get())
            add("implementation", libs.findLibrary("androidx.compose.ui").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.testManifest").get())
        }
    }

    extensions.getByType<ComposeCompilerGradlePluginExtension>().apply {
        enableStrongSkippingMode.set(true)
        includeSourceInformation.set(true)
    }
}
