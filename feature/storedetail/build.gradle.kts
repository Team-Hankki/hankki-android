import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.storedetail")
}

dependencies {
    //domain
    implementation(projects.domain.storedetail)

    //other
    implementation(libs.coil.compose)
}
