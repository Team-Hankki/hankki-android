import com.hankki.build_logic.setNamespace
import java.util.Properties

plugins {
    alias(libs.plugins.hankki.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("core.network")

    val properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    buildTypes {
        debug {
            val devUrl = properties["hankkiDevUrl"] as? String ?: ""
            buildConfigField("String", "BASE_URL", devUrl)

            val devAmplitude = properties["hankkiDevAmplitude"] as? String ?: ""
            buildConfigField("String", "AMPLITUDE_KEY", devAmplitude)
        }

        release {
            val prodUrl = properties["hankkiProdUrl"] as? String ?: ""
            buildConfigField("String", "BASE_URL", prodUrl)

            val prodAmplitude = properties["hankkiProdAmplitude"] as? String ?: ""
            buildConfigField("String", "AMPLITUDE_KEY", prodAmplitude)
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    //core
    implementation(projects.core.datastore)

    //domain
    implementation(projects.domain.reissuetoken)

    //feature
    implementation(projects.feature.main)

    //others
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)

    implementation(libs.jakewharton.process.phoenix)
}
