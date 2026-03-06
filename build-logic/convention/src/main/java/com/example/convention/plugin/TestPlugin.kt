package com.example.convention.plugin

import com.example.convention.util.getLibrary
import com.example.convention.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class TestPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("testImplementation", libs.getLibrary("junit"))
                add("androidTestImplementation", libs.getLibrary("androidx-junit"))
                add("androidTestImplementation", libs.getLibrary("espresso-core"))
            }
        }
    }
}