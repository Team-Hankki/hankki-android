import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.home")
}

dependencies {
    // domain
    implementation(projects.domain.home)

    // others
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
}
