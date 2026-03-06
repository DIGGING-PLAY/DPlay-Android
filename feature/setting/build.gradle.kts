plugins {
    alias(libs.plugins.dplay.feature)
    alias(libs.plugins.dplay.hilt)
}

android {
    namespace = "com.dplay.setting"

    defaultConfig {
        val appVersion = libs.versions.versionName.get()
        buildConfigField("String", "APP_VERSION", "\"$appVersion\"")
    }

    buildFeatures {
        buildConfig = true
    }
}
