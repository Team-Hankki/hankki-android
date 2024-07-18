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
    implementation(projects.domain.token)

    //other
    implementation(libs.coil.compose)
    implementation(libs.jakewharton.process.phoenix)
}


