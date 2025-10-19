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

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("com.android.application")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureComposeAndroid(this)

                defaultConfig {
                    targetSdk = libs.getVersion("targetSdk").requiredVersion.toInt()
                    versionCode = libs.getVersion("versionCode").requiredVersion.toInt()
                    versionName = libs.getVersion("versionName").requiredVersion
                }

                dependencies{
                    add("implementation",libs.getLibrary("timber"))
                }
            }
        }
    }
}
