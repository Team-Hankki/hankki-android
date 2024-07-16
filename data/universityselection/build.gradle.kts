import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.universityselection")
}

dependencies {
    implementation(projects.domain.universityselection)
}
