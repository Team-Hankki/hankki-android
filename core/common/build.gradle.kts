import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.library)
    alias(libs.plugins.hankki.compose)
}

android {
    setNamespace("core.common")
}

dependencies {
    // okhttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.logging)

    // exifinterface
    implementation(libs.exifinterface)
}
