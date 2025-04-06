package com.parksupark.soomjae.buildlogic.multiplatform.core

import com.android.build.gradle.LibraryExtension
import com.parksupark.soomjae.buildlogic.multiplatform.commonConfiguration
import configureAndroid
import fullPackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MultiplatformCoreConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
        }
        commonConfiguration()

        extensions.configure<LibraryExtension> { configureAndroid(project.fullPackageName) }
    }
}
