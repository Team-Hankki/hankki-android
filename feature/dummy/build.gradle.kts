import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.dummy")
}

dependencies {
    implementation(projects.domain.dummy)

    implementation(libs.coil.compose)
}
