import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.report")
}

dependencies {
    // domain
    implementation(projects.domain.report)

    // coil
    implementation(libs.coil.compose)
}
