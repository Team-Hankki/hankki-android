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
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))

    // timber
    implementation(libs.findLibrary("timber").get())

    // immutable
    implementation(libs.findLibrary("kotlinx.immutable").get())
}
