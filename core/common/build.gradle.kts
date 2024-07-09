import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.library)
    alias(libs.plugins.hankki.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("core.common")
}

dependencies {
    implementation(libs.retrofit.kotlin.serialization)
}
