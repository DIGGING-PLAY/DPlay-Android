import com.android.build.api.variant.BuildConfigField
import java.io.StringReader
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

val baseUrl =
    providers
        .fileContents(
            isolated.rootProject.projectDirectory.file("local.properties"),
        ).asText
        .map { text ->
            val properties = Properties()
            properties.load(StringReader(text))
            properties.getProperty("base.url")
        }.orElse("http://example.com")

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
