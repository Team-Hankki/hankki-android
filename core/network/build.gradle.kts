import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("core.network")
}

dependencies{
    implementation(projects.core.common)

    implementation(libs.okhttp.logging)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
}
