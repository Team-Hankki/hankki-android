import com.hankki.build_logic.configureKotlin

plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(17)
}

configureKotlin()
