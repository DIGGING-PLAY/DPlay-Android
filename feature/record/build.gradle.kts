plugins {
    alias(libs.plugins.dplay.feature)
    alias(libs.plugins.dplay.hilt)
}

android {
    namespace = "com.dplay.record"
}

dependencies {
    implementation(libs.coil.compose)
}
