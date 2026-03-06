plugins {
    alias(libs.plugins.dplay.feature)
    alias(libs.plugins.dplay.hilt)
    alias(libs.plugins.dplay.test)
}

android {
    namespace = "com.dplay.main"
}

dependencies {
    implementation(projects.feature.splash)
    implementation(projects.feature.login)
    implementation(projects.feature.home)
    implementation(projects.feature.detail)
    implementation(projects.feature.mypage)
    implementation(projects.feature.search)
    implementation(projects.feature.comment)
    implementation(projects.feature.record)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.setting)
    implementation(projects.feature.editprofile)
    implementation(projects.feature.otherprofile)
}
