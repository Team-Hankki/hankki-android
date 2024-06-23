import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.library)
    alias(libs.plugins.hankki.compose)
}

android {
    setNamespace("core.designsystem")
}

dependencies {
    implementation(libs.androidx.appcompat)
}
