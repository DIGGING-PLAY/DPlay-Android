plugins {
    alias(libs.plugins.dplay.feature)
    alias(libs.plugins.dplay.hilt)
    alias(libs.plugins.dplay.test)
}

android {
    namespace = "com.dplay.main"
}

dependencies{
    // 모든 feature들을 의존
}