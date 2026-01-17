plugins {
    alias(libs.plugins.dplay.feature)
    alias(libs.plugins.dplay.hilt)
}

android {
    namespace = "com.dplay.detail"
}
dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.common)
}
