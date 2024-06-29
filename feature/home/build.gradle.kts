import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.feature)
}

android {
    setNamespace("feature.home")
}

dependencies {
    implementation("io.github.fornewid:naver-map-compose:1.7.2")

    // (Optional) 위치 추적하기
    // 다른 버전의 play-services-location 과 함께 사용하려면 선언해야 합니다.
    // 선언하지 않았을 때, 기본으로 포함되는 play-services-location 버전은 16.0.0 입니다.
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("io.github.fornewid:naver-map-location:21.0.2")
}
