import com.hankki.build_logic.configureHiltAndroid
import com.hankki.build_logic.configureSerializationAndroid
import com.hankki.build_logic.libs

plugins {
    id("hankki.android.library")
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

    // timber
    implementation(libs.findLibrary("timber").get())

    // immutable
    implementation(libs.findLibrary("kotlinx.immutable").get())

    // retrofit
    implementation(libs.findLibrary("retrofit.core").get())
    implementation(libs.findLibrary("retrofit.kotlin.serialization").get())
}
