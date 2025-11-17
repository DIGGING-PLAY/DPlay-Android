plugins {
    alias(libs.plugins.dplay.android.compose)
}

android {
    namespace = "com.dplay.designsystem"
}

dependencies {
    implementation(libs.coil.compose)
}
