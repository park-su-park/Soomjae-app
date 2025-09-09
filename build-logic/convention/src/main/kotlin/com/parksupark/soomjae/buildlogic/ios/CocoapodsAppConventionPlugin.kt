package com.parksupark.soomjae.buildlogic.ios

import SoomjaeConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

class CocoapodsAppConventionPlugin : Plugin<Project> {
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
                        summary = "Kotlin project with CocoaPods Compose dependencies"
                        homepage = "https://github.com/park-su-park/SoomJae-app"

                        podfile = project.file("../iosApp/Podfile")

                        ios.deploymentTarget = SoomjaeConfiguration.IOS_DEPLOYMENT_TARGET

                        framework {
                            baseName = "composeApp"
                            isStatic = true
                        }
                    }
                }
            }
        }
    }
}
