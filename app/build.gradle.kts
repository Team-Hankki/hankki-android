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

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            gradleLocalProperties(rootDir, providers).getProperty("kakaoNativeAppKey"),
        )
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] =
            gradleLocalProperties(rootDir, providers).getProperty("kakaoNative.AppKey")
    }

    buildTypes {
        debug {
            manifestPlaceholders["naverClientId"] =
                gradleLocalProperties(rootDir, providers).getProperty("naverDevClientId")

            manifestPlaceholders["appName"] = "@string/dev_app_name"
        }
        release {
            manifestPlaceholders["naverClientId"] =
                gradleLocalProperties(rootDir, providers).getProperty("naverProdClientId")

            manifestPlaceholders["appName"] = "@string/app_name"
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
    implementation(projects.core.common)

    // other dependencies
    implementation(libs.timber)
    implementation(libs.appcompat)
    implementation(libs.kakao.user)
}
