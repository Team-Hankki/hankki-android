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
}

configureHiltAndroid()
configureSerializationAndroid()

dependencies {
    val libs = project.extensions.libs

    // modules
    implementation(project(":core:network"))

    // timber
    implementation(libs.findLibrary("timber").get())

    // retrofit
    implementation(libs.findBundle("retrofit").get())
}
