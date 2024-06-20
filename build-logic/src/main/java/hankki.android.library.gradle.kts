import com.hankki.build_logic.configureCoroutineAndroid
import com.hankki.build_logic.configureHiltAndroid
import com.hankki.build_logic.configureKotest
import com.hankki.build_logic.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureKotest()
configureCoroutineAndroid()
configureHiltAndroid()
