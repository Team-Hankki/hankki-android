import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.university")
}

dependencies {
    implementation(projects.domain.dummy)
    implementation(libs.androidx.lifecycle.runtimeCompose)
}
