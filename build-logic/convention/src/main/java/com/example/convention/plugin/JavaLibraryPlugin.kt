package com.example.convention.plugin

import com.example.convention.util.getLibrary
import com.example.convention.util.getVersion
import com.example.convention.util.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

class JavaLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("org.jetbrains.kotlin.jvm")
                apply("java-library")
            }

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            extensions.configure<KotlinProjectExtension> {
                jvmToolchain(libs.getVersion("jdkVersion").requiredVersion.toInt())
            }

            dependencies {
                add("implementation", libs.getLibrary("javax.inject"))
                add("implementation", libs.getLibrary("kotlinx.coroutines.core"))
            }
        }
    }
}