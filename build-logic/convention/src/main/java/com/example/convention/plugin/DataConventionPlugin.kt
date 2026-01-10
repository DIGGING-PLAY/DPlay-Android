package com.example.convention.plugin

import com.example.convention.util.getBundle
import com.example.convention.util.getLibrary
import com.example.convention.util.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DataConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("dplay.android.library")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                val retrofitBom = libs.getLibrary("retrofit-bom")
                val okhttpBom = libs.getLibrary("okhttp-bom")
                add("implementation",platform(retrofitBom))
                add("implementation",platform(okhttpBom))
                add("implementation",libs.getLibrary("kotlinx.serialization.json"))
                add("implementation",libs.getBundle("retrofit"))
                add("implementation",libs.getBundle("okhttp"))
            }
        }
    }
}