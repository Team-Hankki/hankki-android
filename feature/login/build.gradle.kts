import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.login")
}

dependencies {
    implementation(libs.kakao.all)
    implementation(libs.kakao.user)
    implementation(libs.androidx.lifecycle.runtimeCompose)
}