plugins {
    alias(libs.plugins.dplay.android.application)
    alias(libs.plugins.dplay.hilt)
    alias(libs.plugins.dplay.test)
}

android {
    namespace = "com.dplay"

    defaultConfig {
        applicationId = "com.dplay"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(projects.feature.main)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    testImplementation(kotlin("test"))
}
