import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.university")
}

dependencies {
    //domain
    implementation(projects.domain.universityselection)

    //androidx
    implementation(libs.androidx.lifecycle.runtimeCompose)
}
