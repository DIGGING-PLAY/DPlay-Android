plugins {
    alias(libs.plugins.dplay.android.compose)
    alias(libs.plugins.dplay.hilt)
}

android {
    namespace = "com.dplay.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.core.designsystem)
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.ui)
}
