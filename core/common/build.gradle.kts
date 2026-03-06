plugins {
    alias(libs.plugins.dplay.android.library)
    alias(libs.plugins.dplay.android.compose)
    alias(libs.plugins.dplay.hilt)
}

android {
    namespace = "com.dplay.common"
}

dependencies {
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.session)
}
