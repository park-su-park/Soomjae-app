package com.parksupark.soomjae.buildlogic.multiplatform.data

import androidx.room.gradle.RoomExtension
import bundleImplementation
import com.parksupark.soomjae.buildlogic.multiplatform.desktopMain
import com.parksupark.soomjae.buildlogic.multiplatform.desktopTest
import kspDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformDataPreferencesConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("katana.multiplatform.core")
            apply("org.jetbrains.kotlin.plugin.serialization")
            apply("androidx.room")
        }

        with(extensions) {
            configure<KotlinMultiplatformExtension> { configureMultiplatform(project) }
            configure<RoomExtension> { configureRoom(project) }
        }
    }

    private fun KotlinMultiplatformExtension.configureMultiplatform(project: Project) {
        configureSourceSets()
        kspDependencies(project, "data-preferences")
    }

    private fun RoomExtension.configureRoom(project: Project) {
        schemaDirectory("${project.projectDir}/schemas")
    }

    private fun KotlinMultiplatformExtension.configureSourceSets() {
        sourceSets {
            commonMain.dependencies {
                bundleImplementation("data-preferences-common")
            }
            androidMain.dependencies {
                bundleImplementation("data-preferences-android")
            }
            iosMain.dependencies {
                bundleImplementation("data-preferences-ios")
            }
            desktopMain.dependencies {
                bundleImplementation("data-preferences-desktop")
            }

            commonTest.dependencies {
                bundleImplementation("data-preferences-common-test")
            }
            androidUnitTest.dependencies {
                bundleImplementation("data-preferences-android-test")
            }
            iosTest.dependencies {
                bundleImplementation("data-preferences-ios-test")
            }
            desktopTest.dependencies {
                bundleImplementation("data-preferences-desktop-test")
            }
        }
    }
}
