plugins {
    alias(libs.plugins.dplay.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.dplay.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
