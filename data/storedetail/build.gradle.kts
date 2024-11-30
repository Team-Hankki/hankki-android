import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.storedetail")
}

dependencies {
    // domain
    implementation(projects.domain.storedetail)
}
