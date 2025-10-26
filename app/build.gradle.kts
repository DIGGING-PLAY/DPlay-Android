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
    testImplementation(kotlin("test"))
}
