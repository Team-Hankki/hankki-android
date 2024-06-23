plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "hankki.android.hilt"
            implementationClass = "com.hankki.build-logic.HiltAndroidPlugin"
        }
        register("kotlinHilt") {
            id = "chattymin.kotlin.hilt"
            implementationClass = "com.hankki.build-logic.HiltKotlinPlugin"
        }
    }
}
