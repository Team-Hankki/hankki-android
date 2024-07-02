import com.hankki.build_logic.configureHiltAndroid
import com.hankki.build_logic.configureSerializationAndroid
import com.hankki.build_logic.libs

plugins {
    id("hankki.android.library")
    id("hankki.android.compose")
}

android {
    packaging {
        resources {
            excludes.add("META-INF/**")
        }
    }
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

configureHiltAndroid()
configureSerializationAndroid()

dependencies {
    val libs = project.extensions.libs

    // modules
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:navigation"))

    // navigation
    implementation(libs.findLibrary("hilt.navigation.compose").get())
    implementation(libs.findLibrary("androidx.compose.navigation").get())
    androidTestImplementation(libs.findLibrary("androidx.compose.navigation.test").get())

    // lifecycle
    implementation(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
    implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())

    // timber
    implementation(libs.findLibrary("timber").get())

    // immutable
    implementation(libs.findLibrary("kotlinx.immutable").get())
}
