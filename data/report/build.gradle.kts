import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.report")
}

dependencies {
    // domain
    implementation(projects.domain.report)
}
