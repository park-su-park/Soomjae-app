@file:Suppress("NoUnusedImports", "UnusedImports")

import com.android.build.gradle.BaseExtension
import java.util.Locale
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.androidJvm
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.common
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.jvm
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.native
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.util.visibleName

private val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.catalogLib(alias: String) = libs.findLibrary(alias).get()

internal val Project.fullPackageName
    get() = SoomjaeConfiguration.PACKAGE_NAME + path.replace(
        ':',
        '.',
    )

internal val Test.isRelease
    get() = name.contains(
        """beta|release""".toRegex(RegexOption.IGNORE_CASE),
    )

internal fun KotlinDependencyHandler.bundleImplementation(alias: String) {
    project.optionalCatalogBundle(alias).ifPresent { bundle -> implementation(bundle) }
}

private fun Project.optionalCatalogBundle(alias: String) = libs.findBundle(alias)

internal fun KotlinMultiplatformExtension.kspDependencies(
    project: Project,
    catalogPrefix: String,
) {
    kspDependencies(project, "", catalogPrefix)
    kspDependencies(project, "Test", catalogPrefix)
}

private fun KotlinMultiplatformExtension.kspDependencies(
    project: Project,
    configurationNameSuffix: String,
    catalogPrefix: String,
) {
    project.dependencies {
        targets.forEach { target ->
            val configurationName = "ksp${target.configurationName(configurationNameSuffix)}"
            val groupName = "${target.groupName}${configurationNameSuffix.suffix}"
            val catalogAlias = "$catalogPrefix-$groupName-ksp".lowercase()

            project.optionalCatalogBundle(catalogAlias).ifPresent { bundle ->
                add(configurationName, bundle)
            }
        }
    }
}

internal fun BaseExtension.configureAndroid(packageName: String) {
    compileSdkVersion(SoomjaeConfiguration.COMPILE_SDK)
    buildToolsVersion(SoomjaeConfiguration.BUILD_TOOLS)

    buildFeatures.buildConfig = false
    namespace = packageName

    defaultConfig {
        minSdk = SoomjaeConfiguration.MIN_SDK
        targetSdk = SoomjaeConfiguration.TARGET_SDK
        versionCode = SoomjaeConfiguration.VERSION_CODE
        versionName = SoomjaeConfiguration.VERSION_NAME

        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = SoomjaeConfiguration.UseJavaVersion
        targetCompatibility = SoomjaeConfiguration.UseJavaVersion
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
            all { test ->
                test.useJUnitPlatform()
                test.enabled = !test.isRelease
            }
        }
    }
}

internal fun TaskContainer.commonTasks() {
    withType<JavaCompile>().configureEach {
        sourceCompatibility = SoomjaeConfiguration.JvmTargetStr
        targetCompatibility = SoomjaeConfiguration.JvmTargetStr
    }
    withType<KotlinCompile>().configureEach {
        compilerOptions.configureKotlinCompiler()
    }
}

private fun KotlinTarget.configurationName(suffix: String) = if (platformType == common) {
    "CommonMainMetadata"
} else {
    "${
        targetName.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else {
                it.toString()
            }
        }
    }$suffix"
}

private val String.suffix get() = if (isNotEmpty()) "-$this" else ""

private val KotlinTarget.groupName
    get() = when {
        platformType == native && targetName.contains(IOS_TARGET) -> IOS_TARGET
        platformType == androidJvm -> ANDROID_TARGET
        platformType == jvm -> DESKTOP_TARGET
        else -> platformType.visibleName
    }

internal fun ExtensionContainer.commonExtensions() {
    configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(SoomjaeConfiguration.JvmTargetStr)
            vendor = JvmVendorSpec.AZUL
        }
    }

    configure<KotlinProjectExtension> {
        jvmToolchain {
            languageVersion = JavaLanguageVersion.of(SoomjaeConfiguration.JvmTargetStr)
            vendor = JvmVendorSpec.AZUL
        }
    }
}

internal fun KotlinCommonCompilerOptions.configureKotlinCompiler() {
    if (this is KotlinJvmCompilerOptions) {
        jvmTarget = SoomjaeConfiguration.JvmTarget
    }
    apiVersion = SoomjaeConfiguration.KotlinVersion
    languageVersion = SoomjaeConfiguration.KotlinVersion
    freeCompilerArgs.addAll(
        "-opt-in=kotlin.RequiresOptIn",
        "-Xexpect-actual-classes",
    )
}

private const val ANDROID_TARGET = "android"
private const val IOS_TARGET = "ios"
private const val DESKTOP_TARGET = "desktop"
