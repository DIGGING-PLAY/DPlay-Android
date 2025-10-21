package com.example.convention.plugin

import com.android.build.api.dsl.LibraryExtension
import com.example.convention.util.configureKotlinAndroid
import com.example.convention.util.getLibrary
import com.example.convention.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }

            dependencies {
                add("implementation",libs.getLibrary("timber"))
            }
        }
    }
}