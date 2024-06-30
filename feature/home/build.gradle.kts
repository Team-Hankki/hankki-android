import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.home")
}

dependencies {
    // naver map
    implementation(libs.naver.map.compose)
    implementation (libs.play.services.location)
    implementation (libs.naver.map.location)
}
