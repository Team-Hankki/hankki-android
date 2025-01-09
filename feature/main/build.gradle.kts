import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

val properties = gradleLocalProperties(rootDir, providers)

android {
    setNamespace("feature.main")

    defaultConfig {
        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            properties.getProperty("kakaoNativeAppKey"),
        )
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties.getProperty("kakaoNative.AppKey")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // feature
    implementation(projects.feature.home)
    implementation(projects.feature.report)
    implementation(projects.feature.my)
    implementation(projects.feature.login)
    implementation(projects.feature.universityselection)
    implementation(projects.feature.storedetail)

    //domain
    implementation(projects.domain.token)

    // others
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.accompanist.systemuicontroller)
}
