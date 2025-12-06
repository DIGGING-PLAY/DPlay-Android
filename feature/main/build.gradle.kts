plugins {
    alias(libs.plugins.dplay.feature)
    alias(libs.plugins.dplay.hilt)
    alias(libs.plugins.dplay.test)
}

android {
    namespace = "com.dplay.main"
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.mypage)
    implementation(projects.feature.recommend)
}
