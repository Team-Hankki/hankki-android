import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("core.data")
}

dependencies {
}
