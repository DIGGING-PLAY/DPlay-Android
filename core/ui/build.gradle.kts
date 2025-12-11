plugins {
    alias(libs.plugins.dplay.android.compose)
}

android {
    namespace = "com.dplay.ui"
}

dependencies {
    implementation(projects.core.designsystem)
}
