import com.hankki.build_logic.configureHiltAndroid
import com.hankki.build_logic.configureKotestAndroid
import com.hankki.build_logic.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
configureKotestAndroid()
