import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.library)
}

android {
    setNamespace("data.dummy")
}

dependencies {
    implementation(projects.domain.dummy)
}
