package com.parksupark.soomjae.buildlogic.multiplatform.core

import bundleImplementation
import com.android.build.gradle.LibraryExtension
import com.parksupark.soomjae.buildlogic.multiplatform.desktopMain
import com.parksupark.soomjae.buildlogic.multiplatform.hierarchy
import commonTasks
import configureAndroid
import fullPackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformCoreTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        apply(plugin = "org.jetbrains.kotlin.multiplatform")
        apply(plugin = "com.android.library")

        with(extensions) {
            configure<KotlinMultiplatformExtension> { configureMultiplatform() }
            configure<LibraryExtension> { configureAndroid(project) }
        }

        tasks.commonTasks()
    }

    private fun KotlinMultiplatformExtension.configureMultiplatform() {
        hierarchy()
        configureSourceSets()
    }

    private fun LibraryExtension.configureAndroid(project: Project) {
        configureAndroid(project.fullPackageName)

        with(packagingOptions.resources.excludes) {
            add("/META-INF/*")
            add("DebugProbesKt.bin")
        }
    }

    private fun KotlinMultiplatformExtension.configureSourceSets() {
        sourceSets {
            commonMain.dependencies {
                bundleImplementation("core-common-test")
                bundleImplementation("data-preferences-common-test")
                bundleImplementation("data-remote-common-test")
                bundleImplementation("ui-common-test")
            }
            androidMain.dependencies {
                bundleImplementation("core-android-test")
                bundleImplementation("data-preferences-android-test")
                bundleImplementation("data-remote-android-test")
                bundleImplementation("ui-android-test")
            }
            iosMain.dependencies {
                bundleImplementation("core-ios-test")
                bundleImplementation("data-preferences-ios-test")
                bundleImplementation("data-remote-ios-test")
                bundleImplementation("ui-ios-test")
            }
            desktopMain.dependencies {
                bundleImplementation("core-desktop-test")
                bundleImplementation("data-preferences-desktop-test")
                bundleImplementation("data-remote-desktop-test")
                bundleImplementation("ui-desktop-test")
            }
        }
    }
}
