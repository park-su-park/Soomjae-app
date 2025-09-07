package com.parksupark.soomjae.buildlogic.ios

import SoomjaeConfiguration
import fullPackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

class CocoapodsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.native.cocoapods")
            }

            plugins.withId("org.jetbrains.kotlin.multiplatform") {
                val kotlinExtension = extensions.findByType(KotlinMultiplatformExtension::class.java)
                kotlinExtension?.apply {
                    configure<CocoapodsExtension> {
                        version = SoomjaeConfiguration.VERSION_NAME
                        ios.deploymentTarget = SoomjaeConfiguration.IOS_DEPLOYMENT_TARGET
                        podfile = project.file("../iosApp/Podfile")

                        framework {
                            baseName = project.fullPackageName
                            isStatic = true
                        }
                    }
                }
            }
        }
    }
}
