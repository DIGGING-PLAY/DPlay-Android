import com.android.build.api.variant.BuildConfigField
import java.util.Properties

plugins {
    alias(libs.plugins.dplay.data)
    alias(libs.plugins.dplay.hilt)
}

android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.dplay.network"
}

val baseUrl: Provider<String> =
    providers.provider {
        // 1. 환경 변수 우선 확인 (CI 환경)
        val envUrl = System.getenv("BASE_URL")
        if (!envUrl.isNullOrBlank()) {
            return@provider envUrl
        }

        // 2. local.properties 확인 (로컬 환경)
        val localPropertiesFile =
            isolated.rootProject.projectDirectory
                .file("local.properties")
                .asFile
        if (localPropertiesFile.exists()) {
            val properties = Properties()
            properties.load(localPropertiesFile.inputStream())
            val url = properties.getProperty("base.url")
            if (!url.isNullOrBlank()) {
                return@provider url
            }
        }

        // 3. 기본값
        "http://example.com"
    }

androidComponents {
    onVariants {
        it.buildConfigFields!!.put(
            "BASE_URL",
            baseUrl.map { value ->
                BuildConfigField(type = "String", value = "\"$value\"", comment = null)
            },
        )
    }
}
