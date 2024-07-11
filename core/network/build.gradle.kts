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
        }

        release {
            val prodUrl = properties["hankkiProdUrl"] as? String ?: ""
            buildConfigField("String", "BASE_URL", prodUrl)
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)

    implementation(libs.jakewharton.process.phoenix)

    //core
    implementation(projects.core.datastore)

    //feature
    implementation(projects.feature.main)
}
