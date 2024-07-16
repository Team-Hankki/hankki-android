import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.reissuetoken")
}

dependencies {
    implementation(projects.domain.reissuetoken)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
}
