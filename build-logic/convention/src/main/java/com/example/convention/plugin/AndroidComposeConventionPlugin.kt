package com.example.convention.plugin

import com.android.build.api.dsl.LibraryExtension
import com.example.convention.util.configureComposeAndroid
import com.example.convention.util.getLibrary
import com.example.convention.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("dplay.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jlleitschuh.gradle.ktlint")
            }

            extensions.configure<LibraryExtension> {
                configureComposeAndroid(this)
            }

            dependencies {
                add("implementation",libs.getLibrary("kotlinx.immutable"))
            }
        }
    }
}