import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.application)
    alias(libs.plugins.android.application)
    alias(libs.plugins.baselineprofile)
}

val properties = gradleLocalProperties(rootDir, providers)

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
            properties.getProperty("kakaoNativeAppKey"),
        )
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties.getProperty("kakaoNative.AppKey")
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = File("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
            storeFile = File("${project.rootDir.absolutePath}/keystore/hankkiKeyStore")
            storePassword = properties.getProperty("storePassword")
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"

            manifestPlaceholders["naverClientId"] = properties.getProperty("naverDevClientId")

            manifestPlaceholders["appName"] = "@string/dev_app_name"
            manifestPlaceholders["appIcon"] = "@mipmap/dev_ic_launcher"
            manifestPlaceholders["roundAppIcon"] = "@mipmap/dev_ic_launcher_round"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")

            manifestPlaceholders["naverClientId"] = properties.getProperty("naverProdClientId")

            manifestPlaceholders["appName"] = "@string/app_name"
            manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
            manifestPlaceholders["roundAppIcon"] = "@mipmap/ic_launcher_round"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // baselineProfile
    baselineProfile(projects.baselineprofile)

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

    // other dependencies
    implementation(libs.timber)
    implementation(libs.appcompat)
    implementation(libs.kakao.user)
    implementation(libs.androidx.profileinstaller)
}
