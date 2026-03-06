import java.io.StringReader
import java.util.Properties

plugins {
    alias(libs.plugins.dplay.android.application)
    alias(libs.plugins.dplay.hilt)
    alias(libs.plugins.dplay.test)
}

val localProperties =
    providers
        .fileContents(isolated.rootProject.projectDirectory.file("local.properties"))
        .asText
        .map { text ->
            val props = Properties()
            props.load(StringReader(text))
            props
        }

val kakaoNativeKey: String =
    providers.gradleProperty("KAKAO_APP_KEY").orNull
        ?: System.getenv("KAKAO_APP_KEY")
        ?: localProperties.get().getProperty("kakao.app.key")
        ?: throw GradleException("KAKAO_APP_KEY (or local kakao.app.key) is missing")

android {
    namespace = "com.diggingplay"

    defaultConfig {
        applicationId = "com.diggingplay"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KAKAO_APP_KEY", "\"$kakaoNativeKey\"")
        manifestPlaceholders["kakaoScheme"] = "kakao$kakaoNativeKey"
    }
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.core.navigation)
    implementation(projects.core.data)
    testImplementation(kotlin("test"))
    implementation(libs.kakao.user)
    implementation(libs.androidx.work.runtime.ktx)
}
