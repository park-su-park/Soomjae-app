package com.parksupark.soomjae.buildlogic.common

import com.charleskorn.kaml.Yaml
import com.github.gmazzo.buildconfig.BuildConfigExtension
import com.parksupark.soomjae.buildlogic.common.Environment.BuildConfig
import java.io.File
import java.util.Properties
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

private const val STRING = "String"

private const val BOOLEAN = "Boolean"

class BuildConfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.github.gmazzo.buildconfig")
            }

            configure<BuildConfigExtension> {
                useKotlinOutput()

                val flavor = providers.gradleProperty("soomjae.flavor").getOrElse("dev")

                val buildConfig = loadBuildConfig(
                    rootProject.file(BUILD_CONFIG),
                ).fromFlavor(flavor)

                val localProps = loadLocalProperties(project, flavor)

                (buildConfig + localProps).forEach {
                    when (it.type) {
                        STRING -> buildConfigField(STRING, it.name, "\"${it.value}\"")
                        BOOLEAN -> buildConfigField(BOOLEAN, it.name, it.value.toBoolean())
                        else -> throw IllegalArgumentException("Unsupported type: ${it.type}")
                    }
                }
            }
        }
    }
}

private fun loadBuildConfig(config: File): Environment = Yaml.default.decodeFromString(
    serializer(),
    config.readText(),
)

private fun loadLocalProperties(
    project: Project,
    flavor: String,
): Set<BuildConfig> = with(project) {
    val props = Properties()

    val commonFile = rootProject.file(LOCAL_PROPERTIES)
    val flavorFile = rootProject.file("local-$flavor.properties")

    if (!commonFile.exists()) return emptySet()

    val commonProps = Properties().apply {
        commonFile.reader().use { load(it) }
    }

    val flavorProps = Properties().apply {
        if (flavorFile.exists()) {
            flavorFile.reader().use { load(it) }
        }
    }

    val duplicates = flavorProps.keys.filter { it in commonProps.keys }
    if (duplicates.isNotEmpty()) {
        duplicates.forEach { key ->
            logger.info(
                "i: Duplicate key '$key' found in local properties for flavor '$flavor'. " +
                    "Using value from 'local-$flavor.properties'.",
            )
        }
    }

    props.apply {
        putAll(commonProps)
        putAll(flavorProps)
    }

    val configKeys = listOf(
        Triple("base.url", STRING, "BASE_URL"),
        Triple("oauth.google.server.client.id", STRING, "OAUTH_GOOGLE_SERVER_CLIENT_ID"),
    )

    val buildConfigs = configKeys.mapNotNull { (propKey, type, buildConfigName) ->
        props.getProperty(propKey)?.let {
            BuildConfig(
                type = type,
                name = buildConfigName,
                value = it,
            )
        } ?: run {
            logger.warn(
                "⚠\uFE0F w: Key '$propKey' not found in local properties for flavor '$flavor'.",
            )
            null
        }
    }

    return buildConfigs.toSet()
}

@Serializable
internal data class Environment(
    @SerialName("dev") val dev: Set<BuildConfig>,
    @SerialName("internal") val internal: Set<BuildConfig>,
    @SerialName("release") val release: Set<BuildConfig>,
) {
    @Serializable
    data class BuildConfig(
        @SerialName("type") val type: String,
        @SerialName("name") val name: String,
        @SerialName("value") val value: String,
    )

    fun fromFlavor(flavor: String) = when (flavor) {
        "dev" -> dev
        "internal" -> internal
        "release" -> release
        else -> dev
    }
}

private const val LOCAL_PROPERTIES = "local.properties"
private const val BUILD_CONFIG = "gradle/config/build_config.yml"
