package com.parksupark.soomjae.buildlogic.analysis

import SoomjaeConfiguration
import catalogLib
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

internal class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("io.gitlab.arturbosch.detekt")
        }

        tasks.register<Detekt>("detektAll") {
            description = "Run detekt in all modules"

            parallel = true
            ignoreFailures = false
            autoCorrect = true
            buildUponDefaultConfig = true
            jvmTarget = SoomjaeConfiguration.JvmTargetStr
            setSource(files(projectDir))
            config.setFrom(files("$rootDir/gradle/config/detekt/detekt.yml"))
            config.setFrom(files("$rootDir/gradle/config/detekt/compose-rules.yml"))
            include("**/*.kt", "**/*.kts")
            exclude("**/resources/**", "**/build/**")

            reports {
                html.required = true
                sarif.required = true
                txt.required = false
                xml.required = true
            }
        }

        dependencies {
            detekt(catalogLib("detekt-compose"))
            detekt(catalogLib("detekt-compose2"))
            detekt(catalogLib("detekt-formatting"))
        }
    }
}

private fun DependencyHandlerScope.detekt(provider: Provider<*>) {
    "detektPlugins"(provider)
}
