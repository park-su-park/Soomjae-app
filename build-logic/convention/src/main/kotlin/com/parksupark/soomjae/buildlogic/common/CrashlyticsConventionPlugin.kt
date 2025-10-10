package com.parksupark.soomjae.buildlogic.common

import catalogLib
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CrashlyticsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.google.gms.google-services")
            apply("com.google.firebase.crashlytics")
        }
        with(extensions) {
            configure<KotlinMultiplatformExtension> {
                sourceSets {
                    androidMain.dependencies {
                        implementation(project.dependencies.platform(catalogLib("firebase-bom")))
                        implementation(catalogLib("firebase-analytics"))
                        implementation(catalogLib("firebase-crashlytics"))
                    }
                }
            }
        }

        // TODO: impelement iOS Crashlytics support
    }
}
