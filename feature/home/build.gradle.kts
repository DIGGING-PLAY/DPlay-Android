plugins {
    alias(libs.plugins.dplay.feature)
    alias(libs.plugins.dplay.hilt)
    alias(libs.plugins.dplay.test)
}

android {
    namespace = "com.dplay.home"
}

dependencies {
    implementation(projects.core.ui)
}
