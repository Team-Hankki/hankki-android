import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.login")
}

dependencies {
    implementation(libs.kakao.user)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(projects.domain.login)
    implementation(projects.domain.token)
}