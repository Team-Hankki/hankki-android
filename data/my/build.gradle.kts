// data
import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.my")
}

dependencies {
    implementation(projects.domain.my)
}
