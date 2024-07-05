import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.my")
}

dependencies {
    implementation(libs.coil.compose)
    implementation(projects.domain.my)
}

