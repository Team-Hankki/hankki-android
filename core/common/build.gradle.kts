import com.hankki.build_logic.setNamespace
import java.util.Properties

plugins {
    alias(libs.plugins.hankki.library)
    alias(libs.plugins.hankki.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("core.common")

    val properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    buildTypes {
        debug {
            val devAmplitude = properties["amplitudeDevKey"] as? String ?: ""
            buildConfigField("String", "AMPLITUDE_KEY", devAmplitude)
        }

        release {
            val prodAmplitude = properties["amplitudeProdKey"] as? String ?: ""
            buildConfigField("String", "AMPLITUDE_KEY", prodAmplitude)
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // okhttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.logging)

    // exifinterface
    implementation(libs.exifinterface)
    
    // serialization
    implementation(libs.retrofit.kotlin.serialization)

    // amplitude
    implementation(libs.amplitude)

    // Timber
    implementation(libs.timber)
}
