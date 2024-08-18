import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.login")
}

dependencies {
    //domain
    implementation(projects.domain.login)
    implementation(projects.domain.token)

    //kakao
    implementation(libs.kakao.user)

    //other
    implementation(libs.lottie.compose)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.tools.core)
    implementation(libs.androidx.lifecycle.runtimeCompose)
}
