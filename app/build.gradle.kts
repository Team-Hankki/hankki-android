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
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.data.dummy)
    implementation(projects.core.network)

    implementation(libs.timber)
}
