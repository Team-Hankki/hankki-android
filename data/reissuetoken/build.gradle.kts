import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.reissuetoken")
}

dependencies {
    // domain
    implementation(projects.domain.reissuetoken)

    // others
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
}
