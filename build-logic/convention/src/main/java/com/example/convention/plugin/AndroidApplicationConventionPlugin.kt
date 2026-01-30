package com.example.convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.util.configureComposeAndroid
import com.example.convention.util.configureKotlinAndroid
import com.example.convention.util.getLibrary
import com.example.convention.util.getVersion
import com.example.convention.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.io.FileInputStream
import java.util.Properties

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager
                .apply{
                    apply("com.android.application")
                    apply("org.jlleitschuh.gradle.ktlint")
                }

            val keystoreProperties = Properties()
            val keystorePropertiesFile = rootProject.file("local.properties")
            val isLocalPropertiesExists = keystorePropertiesFile.exists()
            if (isLocalPropertiesExists) {
                keystoreProperties.load(FileInputStream(keystorePropertiesFile))
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureComposeAndroid(this)

                defaultConfig {
                    targetSdk = libs.getVersion("targetSdk").requiredVersion.toInt()
                    versionCode = libs.getVersion("versionCode").requiredVersion.toInt()
                    versionName = libs.getVersion("versionName").requiredVersion
                }

                if (isLocalPropertiesExists) {
                    signingConfigs {
                        create("release") {
                            keyAlias = keystoreProperties["keyAlias"] as String?
                            keyPassword = keystoreProperties["keyPassword"] as String?
                            storeFile = (keystoreProperties["storeFile"] as String?)?.let { rootProject.file(it) }
                            storePassword = keystoreProperties["storePassword"] as String?
                        }
                    }
                }

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                        if (isLocalPropertiesExists) {
                            signingConfig = signingConfigs.getByName("release")
                        } else {
                            signingConfig = null
                        }
                    }
                }

                dependencies {
                    add("implementation", libs.getLibrary("timber"))
                }
            }
        }
    }
}
