package com.parksupark.soomjae.buildlogic.multiplatform.ui

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class MultiplatformUiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(plugin = "soomjae.multiplatform.core")
            apply(plugin = "soomjae.multiplatform.compose")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
        }
    }
}
