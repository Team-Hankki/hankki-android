import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.hankki.build_logic.setNamespace
import org.gradle.kotlin.dsl.resolver.SourceDistributionResolver.Companion.artifactType

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

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = File("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = gradleLocalProperties(rootDir, providers).getProperty("keyAlias")
            keyPassword = gradleLocalProperties(rootDir, providers).getProperty("keyPassword")
            storeFile = File("${project.rootDir.absolutePath}/keystore/hankkiKeyStore")
            storePassword = "hankkiandroid"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"

            manifestPlaceholders["naverClientId"] =
                gradleLocalProperties(rootDir, providers).getProperty("naverDevClientId")

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

            manifestPlaceholders["naverClientId"] =
                gradleLocalProperties(rootDir, providers).getProperty("naverProdClientId")

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
