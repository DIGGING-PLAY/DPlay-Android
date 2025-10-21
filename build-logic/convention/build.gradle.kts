plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "dplay.android.application"
            implementationClass = "com.example.convention.plugin.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "dplay.android.library"
            implementationClass = "com.example.convention.plugin.AndroidLibraryConventionPlugin"
        }

        register("androidCompose") {
            id = "dplay.android.compose"
            implementationClass = "com.example.convention.plugin.AndroidComposeConventionPlugin"
        }

        register("feature") {
            id = "dplay.feature"
            implementationClass = "com.example.convention.plugin.FeatureConventionPlugin"
        }

        register("data") {
            id = "dplay.data"
            implementationClass = "com.example.convention.plugin.DataConventionPlugin"
        }

        register("domain"){
            id = "dplay.domain"
            implementationClass = "com.example.convention.plugin.JavaLibraryPlugin"
        }

        register("hilt") {
            id = "dplay.hilt"
            implementationClass = "com.example.convention.plugin.HiltConventionPlugin"
        }
    }
}