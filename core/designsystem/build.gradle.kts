import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.library)
    alias(libs.plugins.hankki.compose)
}

android {
    setNamespace("core.designsystem")
}

dependencies {
    // core
    implementation(projects.core.common)

    // others
    implementation(libs.androidx.appcompat)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.immutable)
    implementation(libs.lottie.compose)
}
