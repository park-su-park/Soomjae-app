@file:Suppress("NoUnusedImports", "UnusedImports")

package com.parksupark.soomjae.buildlogic.multiplatform.ui

import bundleImplementation
import com.parksupark.soomjae.buildlogic.multiplatform.desktopMain
import com.parksupark.soomjae.buildlogic.multiplatform.desktopTest
import fullPackageName
import java.io.File
import kspDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.compose.resources.ResourcesExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag.Companion.OptimizeNonSkippingGroups
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("org.jetbrains.compose")
            apply("org.jetbrains.kotlin.plugin.compose")
            apply("org.jetbrains.compose.hot-reload")
        }

        with(extensions) {
            configure<KotlinMultiplatformExtension> { configureMultiplatform(project) }
            configure<ComposeExtension> { configureComposeResources(project) }
            configure<ComposeCompilerGradlePluginExtension> { configureComposeCompiler(project) }
        }
    }

    private fun KotlinMultiplatformExtension.configureMultiplatform(project: Project) {
        configureSourceSets()

        kspDependencies(project, "ui")
    }

    private fun KotlinMultiplatformExtension.configureSourceSets() {
        val compose = (this as ExtensionAware).extensions.getByType<ComposePlugin.Dependencies>()

        sourceSets {
            commonMain.dependencies {
                implementation(compose.animation)
                implementation(compose.components.resources)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.runtime)
                implementation(compose.runtimeSaveable)
                implementation(compose.ui)
                bundleImplementation("ui-common")
            }
            androidMain.dependencies {
                implementation(compose.preview)
                implementation(compose.uiTooling)
                bundleImplementation("ui-android")
            }
            iosMain.dependencies {
                bundleImplementation("ui-ios")
            }
            desktopMain.dependencies {
                implementation(compose.preview)
                implementation(compose.uiTooling)
                bundleImplementation("ui-desktop")
            }

            commonTest.dependencies {
                bundleImplementation("ui-common-test")
            }
            androidUnitTest.dependencies {
                bundleImplementation("ui-android-test")
            }
            iosTest.dependencies {
                bundleImplementation("ui-ios-test")
            }
            desktopTest.dependencies {
                bundleImplementation("ui-desktop-test")
            }
        }
    }

    private fun ComposeCompilerGradlePluginExtension.configureComposeCompiler(project: Project) {
        featureFlags.add(OptimizeNonSkippingGroups)

        metricsDestination = project.file(project.composePluginDir("compose-metrics"))
        reportsDestination = project.file(project.composePluginDir("compose-reports"))
    }

    private fun ComposeExtension.configureComposeResources(project: Project) {
        val resources = (this as ExtensionAware).extensions.getByType<ResourcesExtension>()
        resources.packageOfResClass = "${project.fullPackageName}.resources"
    }

    private fun Project.composePluginDir(directory: String) = File(
        layout.buildDirectory.asFile.get(),
        directory,
    ).absolutePath
}
