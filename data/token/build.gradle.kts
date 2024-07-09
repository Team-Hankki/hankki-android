import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.token")
}
dependencies {
    implementation(projects.domain.token)
    implementation(projects.core.datastore)
}