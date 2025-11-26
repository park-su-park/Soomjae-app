package com.parksupark.soomjae.buildlogic.multiplatform

import apiAlias
import bundleImplementation
import com.google.devtools.ksp.gradle.KspExtension
import commonExtensions
import commonTasks
import kspDependencies
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

internal fun Project.commonConfiguration(
    configureAndroid: KotlinAndroidTarget.() -> Unit = { },
    configureApple: KotlinNativeTarget.() -> Unit = { },
    configureDesktop: KotlinJvmTarget.() -> Unit = { },
) {
    with(pluginManager) {
        apply(plugin = "org.jetbrains.kotlin.multiplatform")
        apply(plugin = "com.google.devtools.ksp")
        apply(plugin = "io.kotest.multiplatform")
        apply(plugin = "dev.mokkery")
    }

    with(extensions) {
        commonExtensions()
        configure<KotlinMultiplatformExtension> {
            configureMultiplatform(
                project = project,
                configureAndroid = configureAndroid,
                configureApple = configureApple,
                configureDesktop = configureDesktop,
            )
        }
    }

    tasks.commonTasks()
}

private fun KotlinMultiplatformExtension.configureMultiplatform(
    project: Project,
    configureAndroid: KotlinAndroidTarget.() -> Unit = { },
    configureApple: KotlinNativeTarget.() -> Unit = { },
    configureDesktop: KotlinJvmTarget.() -> Unit = { },
) {
    hierarchy(
        configureAndroid = configureAndroid,
        configureApple = configureApple,
        configureDesktop = configureDesktop,
    )
    configureSourceSets()

    kspDependencies(project, "core")
    configureKoinAnnotation(project)
}

private fun KotlinMultiplatformExtension.configureKoinAnnotation(project: Project) {
    sourceSets {
        commonMain.dependencies {
            apiAlias("koin-annotation")
        }
    }

    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }

    project.extensions.configure<KspExtension> {
        arg("KOIN_CONFIG_CHECK", "true")

        if (project.logger.isInfoEnabled) {
            arg("KOIN_LOG_TIMES", "true")
        }
    }

    project.tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }
        .configureEach {
            dependsOn("kspCommonMainKotlinMetadata")
        }
}

private fun KotlinMultiplatformExtension.configureSourceSets() {
    sourceSets {
        commonMain.dependencies {
            bundleImplementation("core-common")
        }
        androidMain.dependencies {
            bundleImplementation("core-android")
        }
        iosMain.dependencies {
            bundleImplementation("core-ios")
        }
        desktopMain.dependencies {
            bundleImplementation("core-desktop")
        }

        commonTest.dependencies {
            bundleImplementation("core-common-test")
        }
        androidUnitTest.dependencies {
            bundleImplementation("core-android-test")
        }
        iosTest.dependencies {
            bundleImplementation("core-ios-test")
        }
        desktopTest.dependencies {
            bundleImplementation("core-desktop-test")
        }
    }
}
