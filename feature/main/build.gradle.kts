import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.main")
}

dependencies {
    // feature
    implementation(projects.feature.home)
    implementation(projects.feature.report)
    implementation(projects.feature.my)
    implementation(projects.feature.login)
    implementation(projects.feature.universityselection)
    implementation(projects.feature.storedetail)

    //domain
    implementation(projects.domain.token)

    // others
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.accompanist.systemuicontroller)
}
