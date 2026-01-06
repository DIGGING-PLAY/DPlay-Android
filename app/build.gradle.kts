import java.io.StringReader
import java.util.Properties

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
    implementation(projects.core.navigation)
    testImplementation(kotlin("test"))
    implementation(libs.kakao.user)
}
