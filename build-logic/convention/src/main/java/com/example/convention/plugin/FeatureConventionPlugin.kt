package com.example.convention.plugin

import com.example.convention.util.getBundle
import com.example.convention.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("dplay.android.compose")
            }

            dependencies {
                add("implementation",libs.getBundle("compose"))
                add("implementation",libs.getBundle("navigation"))
                add("implementation",project(":core:designsystem"))
                add("implementation",project(":core:common"))
                add("implementation",project(":core:navigation"))
                add("implementation",project(":core:ui"))
            }
        }
    }
}