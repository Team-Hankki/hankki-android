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

    defaultConfig{
        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            properties.getProperty("kakaoNativeAppKey"),
        )
        buildConfigField(
            "String",
            "KAKAO_SHARE_DEFAULT_IMAGE",
            properties.getProperty("kakaoShare.defaultImage"),
        )
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties.getProperty("kakaoNative.AppKey")
        manifestPlaceholders["KAKAO_SHARE_DEFAULT_IMAGE"] = properties.getProperty("kakaoShare.defaultImage")
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

    // serialization
    implementation(libs.retrofit.kotlin.serialization)

    // amplitude
    implementation(libs.amplitude)

    // Timber
    implementation(libs.timber)
}
