package com.parksupark.soomjae.buildlogic.multiplatform.data

import bundleImplementation
import com.parksupark.soomjae.buildlogic.multiplatform.desktopMain
import com.parksupark.soomjae.buildlogic.multiplatform.desktopTest
import kspDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformDataRemoteConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(plugin = "soomjae.multiplatform.core")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
        }

        with(extensions) {
            configure<KotlinMultiplatformExtension> { configureMultiplatform(project) }
        }
    }

    private fun KotlinMultiplatformExtension.configureMultiplatform(project: Project) {
        configureSourceSets()
        kspDependencies(project, "data-remote")
    }

    private fun KotlinMultiplatformExtension.configureSourceSets() {
        sourceSets {
            commonMain.dependencies {
                bundleImplementation("data-remote-common")
            }
            androidMain.dependencies {
                bundleImplementation("data-remote-android")
            }
            iosMain.dependencies {
                bundleImplementation("data-remote-ios")
            }
            desktopMain.dependencies {
                bundleImplementation("data-remote-desktop")
            }

            commonTest.dependencies {
                bundleImplementation("data-remote-common-test")
            }
            androidUnitTest.dependencies {
                bundleImplementation("data-remote-android-test")
            }
            iosTest.dependencies {
                bundleImplementation("data-remote-ios-test")
            }
            desktopTest.dependencies {
                bundleImplementation("data-remote-desktop-test")
            }
        }
    }
}
