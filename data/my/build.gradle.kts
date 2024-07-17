// data
import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.data)
}

android {
    setNamespace("data.모듈명")
}

dependencies {
    implementation(projects.domain.my)
}
