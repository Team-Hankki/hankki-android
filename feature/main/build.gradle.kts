import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.main")
}

dependencies {
    // feature
    implementation(projects.feature.dummy)
    implementation(projects.feature.home)
    implementation(projects.feature.report)
    implementation(projects.feature.my)
    implementation(projects.feature.login)
}
