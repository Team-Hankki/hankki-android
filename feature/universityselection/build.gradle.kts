import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.universityselection")
}

dependencies {
    //domain
    implementation(projects.domain.universityselection)

    //androidx
    implementation(libs.androidx.lifecycle.runtimeCompose)

    //kotlinx
    implementation(libs.kotlinx.immutable)
}
