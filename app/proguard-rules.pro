# 기본 ProGuard 최적화
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes EnclosingMethod

# Kotlin 특화 설정
-keepclassmembers class kotlin.Metadata {
    *;
}
-keep class kotlin.** { *; }
-dontwarn kotlin.**

# Android 기본 구성 요소
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Application
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keepclassmembers enum * { *; }
-keepclassmembers class * {
    public void *(android.os.Bundle);
    public void *(android.view.Menu, android.view.MenuItem);
}
-dontwarn android.**

# Kotlin Serialization에서 사용하는 클래스 유지
-keep class kotlinx.serialization.** { *; }

# 직렬화 모델 유지
-keep class com.hankki.** { *; }

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Accompanist
-keep class com.google.accompanist.** { *; }
-dontwarn com.google.accompanist.**

# Jetpack Datastore
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# Lifecycle & ViewModel
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class androidx.lifecycle.ViewModel {
    public <init>(...);
}
-dontwarn androidx.lifecycle.**

# Navigation Compose
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# Hilt
-keep class dagger.hilt.** { *; }
-keep class androidx.hilt.** { *; }
-dontwarn dagger.**

# Coil
-keep class coil.** { *; }
-dontwarn coil.**

# Naver Maps
-keep class io.github.fornewid.naver.maps.** { *; }
-dontwarn io.github.fornewid.naver.maps.**

# Retrofit & OkHttp
-keep class com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**

-keep class com.squareup.retrofit2.** { *; }
-keep interface com.squareup.retrofit2.** { *; }
-dontwarn com.squareup.retrofit2.**

# Coroutines
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# Timber
-keep class timber.log.** { *; }
-dontwarn timber.log.**

# Kakao SDK
-keep class com.kakao.sdk.** { *; }
-dontwarn com.kakao.sdk.**

# Amplitude
-keep class com.amplitude.** { *; }
-dontwarn com.amplitude.**

# Lottie
-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

# Androidx ExifInterface
-keep class androidx.exifinterface.** { *; }
-dontwarn androidx.exifinterface.**

# Espresso & Testing
-keep class androidx.test.espresso.** { *; }
-dontwarn androidx.test.espresso.**
-keep class androidx.test.ext.junit.** { *; }
-dontwarn androidx.test.ext.junit.**

# 기타 설정
-dontnote okhttp3.**
-dontnote retrofit2.**
-dontnote kotlinx.coroutines.**

# 최적화 방지 (특정 문제 디버깅 시 활성화 가능)
# -dontoptimize
