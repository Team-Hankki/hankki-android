import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.my")
}

dependencies {
    //domain
    implementation(projects.domain.my)

    //other
    implementation(libs.coil.compose)
}


