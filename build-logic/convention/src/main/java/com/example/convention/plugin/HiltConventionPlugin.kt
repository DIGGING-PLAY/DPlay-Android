package com.example.convention.plugin

import com.example.convention.util.getLibrary
import com.example.convention.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation",libs.getLibrary("hilt.android"))
                add("ksp",libs.getLibrary("hilt.android.compiler"))
            }
        }
    }
}