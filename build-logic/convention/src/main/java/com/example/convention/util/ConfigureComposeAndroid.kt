package com.example.convention.util

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureComposeAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val composeBom = libs.getLibrary("compose-bom")
            add("implementation",platform(composeBom))
            add("androidTestImplementation", platform(composeBom))
            add("implementation",libs.getBundle("compose"))
            add("debugImplementation",libs.getLibrary("compose-ui-tooling"))
        }
    }
}