import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.application)
}

android {
    setNamespace("hankkijogbo")

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.hankki.hankkijogbo"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        manifestPlaceholders["naverClientId"] =
            gradleLocalProperties(rootDir, providers).getProperty("naverClientId")

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            gradleLocalProperties(rootDir, providers).getProperty("kakaoNativeAppKey"),
        )
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] =
            gradleLocalProperties(rootDir, providers).getProperty("kakaoNative.AppKey")
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // feature
    implementation(projects.feature.main)

    // data
    implementation(projects.data.dummy)
    implementation(projects.data.token)
    implementation(projects.data.login)
    implementation(projects.data.reissuetoken)
    implementation(projects.data.home)
    implementation(projects.data.report)
    implementation(projects.data.storedetail)
    implementation(projects.data.my)
    implementation(projects.data.universityselection)

    // core
    implementation(projects.core.network)
    implementation(projects.core.datastore)

    // other dependencies
    implementation(libs.timber)
    implementation(libs.appcompat)
    implementation(libs.kakao.user)
}
