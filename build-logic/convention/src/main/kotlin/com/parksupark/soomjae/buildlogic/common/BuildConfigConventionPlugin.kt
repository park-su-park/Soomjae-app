package com.parksupark.soomjae.buildlogic.common

import com.charleskorn.kaml.Yaml
import com.github.gmazzo.buildconfig.BuildConfigExtension
import com.parksupark.soomjae.buildlogic.common.Environment.BuildConfig
import java.io.File
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

                val localProps = loadLocalProperties(project)

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

private fun loadLocalProperties(project: Project): Set<BuildConfig> = with(project) {
    val propertiesFile = rootProject.file(LOCAL_PROPERTIES)
    if (!propertiesFile.exists()) return emptySet()

    val props = java.util.Properties().apply {
        propertiesFile.reader().use { load(it) }
    }

    return listOfNotNull(
        props.getProperty("base.url")?.let {
            BuildConfig(type = STRING, name = "BASE_URL", value = it)
        },
        props.getProperty("oauth.google.server.client.id")?.let {
            BuildConfig(type = STRING, name = "OAUTH_GOOGLE_SERVER_CLIENT_ID", value = it)
        },
    ).toSet()
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
