import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.login")
}

dependencies {
    implementation(projects.domain.login)
}
