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
    //core
    implementation(projects.core.datastore)

    //domain
    implementation(projects.domain.reissuetoken)

    // exifinterface
    implementation(libs.exifinterface)

    //others
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)

    implementation(libs.bundles.retrofit)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)

    implementation(libs.jakewharton.process.phoenix)
}
