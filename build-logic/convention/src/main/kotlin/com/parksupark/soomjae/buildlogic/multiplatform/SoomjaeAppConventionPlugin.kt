@file:Suppress("NoUnusedImports", "UnusedImports")

package com.parksupark.soomjae.buildlogic.multiplatform

import SoomjaeConfiguration
import bundleImplementation
import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import configureAndroid
import java.util.Properties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.internal.cc.base.logger
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class SoomjaeAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
        }

        commonConfiguration()

        // ! Should be applied after commonConfiguration to avoid 'kspCommonMainMetadata' not found error
        with(pluginManager) {
            apply("soomjae.multiplatform.compose")
            apply("org.jetbrains.compose.hot-reload")
            apply("soomjae.crashlytics")
        }

        with(extensions) {
            configure<ComposeExtension> {
                (this as ExtensionAware).extensions
                    .getByType<DesktopExtension>()
                    .configureDesktop(project)
            }
            configure<KotlinMultiplatformExtension> { configureMultiplatform() }

            configure<BaseAppModuleExtension> { configureAndroid(project) }
        }
    }

    private fun KotlinMultiplatformExtension.configureMultiplatform() {
        configureSourceSets()
    }

    private fun KotlinMultiplatformExtension.configureSourceSets() {
        val compose = (this as ExtensionAware).extensions.getByType<ComposePlugin.Dependencies>()

        sourceSets {
            androidMain.dependencies {
                bundleImplementation("app-android")
            }
            iosMain.dependencies {
                bundleImplementation("app-ios")
            }
            desktopMain.dependencies {
                implementation(compose.desktop.currentOs)
                bundleImplementation("app-desktop")
            }
        }
    }

    @Suppress("StringLiteralDuplication")
    private fun BaseAppModuleExtension.configureAndroid(project: Project) {
        fun ApplicationBuildType.configure(isDebug: Boolean) {
            isDebuggable = isDebug
            isMinifyEnabled = !isDebug
            isShrinkResources = !isDebug
            enableUnitTestCoverage = isDebug
        }

        configureAndroid(SoomjaeConfiguration.PACKAGE_NAME)

        compileOptions.isCoreLibraryDesugaringEnabled = true
        defaultConfig.applicationId = SoomjaeConfiguration.PACKAGE_NAME
        lint.abortOnError = false

        with(packagingOptions.resources.excludes) {
            add("/META-INF/{AL2.0,LGPL2.1}")
            add("DebugProbesKt.bin")
        }

        signingConfigs {
            getByName("debug") {
                val props = with(project) {
                    loadProperties("dev")
                }

                props["keystore.path", "KEYSTORE_PATH"]?.let {
                    val file = project.rootProject.file(it)
                    if (file.exists()) {
                        storeFile = file
                    } else {
                        logger.warn(KEYSTORE_FILE_NOT_FOUND, "debug")
                    }
                }
            }
            create("internal") {
                val props = with(project) {
                    loadProperties("internal")
                }

                props["keystore.path", "KEYSTORE_PATH"]?.let {
                    val file = project.rootProject.file(it)
                    if (file.exists()) {
                        storeFile = file
                    } else {
                        logger.warn(KEYSTORE_FILE_NOT_FOUND, "internal")
                    }
                }
                props["keystore.password", "KEYSTORE_PASSWORD"]?.let {
                    storePassword = it
                }
                props["key.alias", "KEY_ALIAS"]?.let {
                    keyAlias = it
                }
                props["key.password", "KEY_PASSWORD"]?.let {
                    keyPassword = it
                }
            }
            create("release") {
                val props = with(project) {
                    loadProperties("release")
                }

                props["keystore.path", "KEYSTORE_PATH"]?.let {
                    val file = project.rootProject.file(it)
                    if (file.exists()) {
                        storeFile = file
                    } else {
                        logger.warn(KEYSTORE_FILE_NOT_FOUND, "release")
                    }
                }
                props["keystore.password", "KEYSTORE_PASSWORD"]?.let {
                    storePassword = it
                }
                props["key.alias", "KEY_ALIAS"]?.let {
                    keyAlias = it
                }
                props["key.password", "KEY_PASSWORD"]?.let {
                    keyPassword = it
                }
            }
        }

        buildTypes {
            debug {
                applicationIdSuffix = ".dev"
                versionNameSuffix = "-dev"

                configure(isDebug = true)
                isDefault = true

                signingConfig = signingConfigs.getByName("debug")
                resValue("string", "app_name", "Soomjae Dev")
            }

            create("internal") {
                initWith(getByName("debug"))
                matchingFallbacks += listOf("debug")
                applicationIdSuffix = ".internal"
                versionNameSuffix = "-internal"

                configure(isDebug = true)

                signingConfig = signingConfigs.getByName("internal")
                resValue("string", "app_name", "Soomjae Internal")
            }

            release {
                configure(isDebug = false)

                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "$ANDROID_DIR/proguard-rules.pro",
                )

                signingConfig = signingConfigs.getByName("release")
                resValue("string", "app_name", "Soomjae")
            }
        }

        sourceSets["main"].manifest.srcFile("$ANDROID_DIR/AndroidManifest.xml")
        sourceSets["main"].res.srcDirs("$ANDROID_DIR/res")
    }

    private fun DesktopExtension.configureDesktop(project: Project) {
        application {
            mainClass = "com.parksupark.soomjae.MainKt"

            buildTypes {
                release {
                    proguard {
                        isEnabled = true
                        obfuscate = true
                    }
                }
            }

            nativeDistributions {
                targetFormats(
                    TargetFormat.Deb,
                    TargetFormat.Rpm,
                    TargetFormat.Dmg,
                    TargetFormat.Exe,
                )

                packageName = "SoomJae"
                packageVersion = SoomjaeConfiguration.VERSION_NAME

                linux {
                    iconFile = project.file("desktopAppIcons/LinuxIcon.png")
                }

                macOS {
                    iconFile = project.file("desktopAppIcons/MacosIcon.icns")
                    dmgPackageVersion = "1"
                    bundleID = "${SoomjaeConfiguration.PACKAGE_NAME}.desktopApp"
                }

                windows {
                    iconFile = project.file("desktopAppIcons/WindowsIcon.ico")
                }
            }
        }
    }

    fun Project.loadProperties(flavor: String): Properties {
        val props = Properties()
        val fileName = "local-$flavor.properties"
        val propFile = project.rootProject.file(fileName)
        if (propFile.exists()) {
            propFile.inputStream().use { props.load(it) }
            logger.lifecycle("✅ Loaded $fileName")
        } else {
            logger.warn("⚠️ $fileName not found")
        }
        return props
    }
}

private operator fun Properties.get(
    key: String,
    env: String,
) = getOrElse(key) { System.getenv(env) } as? String

private const val ANDROID_DIR = "src/androidMain"

private const val KEYSTORE_FILE_NOT_FOUND = "⚠️ Keystore file not found for %s build type."
