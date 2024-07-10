import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.token")
}
dependencies {
    //core
    implementation(projects.core.datastore)

    //domane
    implementation(projects.domain.token)
}