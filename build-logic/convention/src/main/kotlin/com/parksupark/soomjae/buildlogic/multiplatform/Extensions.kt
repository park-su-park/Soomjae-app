package com.parksupark.soomjae.buildlogic.multiplatform

import SoomjaeConfiguration
import configureKotlinCompiler
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinSourceSetConvention
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import java.util.Locale

@OptIn(ExperimentalKotlinGradlePluginApi::class)
internal fun KotlinMultiplatformExtension.hierarchy(
    configureAndroid: KotlinAndroidTarget.() -> Unit = { },
    configureApple: KotlinNativeTarget.() -> Unit = { },
    configureDesktop: KotlinJvmTarget.() -> Unit = { },
) {
    applyDefaultHierarchyTemplate {
        common {
            group("jvmBased") {
                withJvm()
                withAndroidTarget()
            }

            group("mobile") {
                withAndroidTarget()
                withIos()
                withIosX64()
                withIosArm64()
                withIosSimulatorArm64()
            }

            group("nonMobile") {
                withJvm()
            }
        }
    }

    configureAndroid(configureAndroid)
    configureApple(configureApple)
    configureDesktop(configureDesktop)

    configureKotlin()
}

private fun KotlinMultiplatformExtension.configureAndroid(configure: KotlinAndroidTarget.() -> Unit) {
    androidTarget {
        configure()
    }
}

private fun KotlinMultiplatformExtension.configureApple(configure: KotlinNativeTarget.() -> Unit) {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { ios ->
        ios.configure()
        compilerOptions.configureKotlinCompiler()
        ios.binaries.framework {
            baseName = project.frameworkIdentifier
            isStatic = true
        }
    }
}

private fun KotlinMultiplatformExtension.configureDesktop(configure: KotlinJvmTarget.() -> Unit) {
    jvm("desktop") {
        configure()
        testRuns["test"].executionTask.configure { useJUnitPlatform() }
    }
}

private fun KotlinMultiplatformExtension.configureKotlin() {
    jvmToolchain(SoomjaeConfiguration.JvmTargetStr.toInt())
    sourceSets.commonMain { configureCommonLanguageSettings() }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().configureKotlinCompiler()
        }
    }
}

private fun KotlinSourceSet.configureCommonLanguageSettings() {
    languageSettings {
        apiVersion = SoomjaeConfiguration.KotlinVersion.version
        languageVersion = SoomjaeConfiguration.KotlinVersion.version
        progressiveMode = true
    }
}

private fun KotlinCompilationTask<*>.configureKotlinCompiler() {
    compilerOptions.configureKotlinCompiler()
}

private val Project.frameworkIdentifier
    get() = path.split(':').identifier

private val List<String>.identifier
    get() = filter { it.isNotEmpty() }
        .reduceRight { acc, s ->
            "$acc${
                s.replaceFirstChar {
                    if (it.isLowerCase()) {
                        it.titlecase(Locale.getDefault())
                    } else {
                        it.toString()
                    }
                }
            }"
        }

@OptIn(ExperimentalKotlinGradlePluginApi::class)
internal val NamedDomainObjectContainer<KotlinSourceSet>.desktopMain:
    NamedDomainObjectProvider<KotlinSourceSet> by KotlinSourceSetConvention

@OptIn(ExperimentalKotlinGradlePluginApi::class)
internal val NamedDomainObjectContainer<KotlinSourceSet>.desktopTest:
    NamedDomainObjectProvider<KotlinSourceSet> by KotlinSourceSetConvention
