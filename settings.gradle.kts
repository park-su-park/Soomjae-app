rootProject.name = "SoomJae"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
    }
}
plugins {
    // https://github.com/JetBrains/compose-hot-reload?tab=readme-ov-file#set-up-automatic-provisioning-of-the-jetbrains-runtime-jbr-via-gradle
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":composeApp")
include(":core:analytics")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:image")
include(":core:notification:data")
include(":core:notification:domain")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":core:remote")
include(":core:test")
include(":features:auth:data")
include(":features:auth:domain")
include(":features:auth:libs:google")
include(":features:auth:presentation")
include(":features:home:presentation")
include(":features:posts:aggregate:data")
include(":features:posts:aggregate:domain")
include(":features:posts:aggregate:presentation")
include(":features:posts:common:data")
include(":features:posts:common:domain")
include(":features:posts:common:presentation")
include(":features:posts:community:data")
include(":features:posts:community:domain")
include(":features:posts:community:presentation")
include(":features:posts:meeting:data")
include(":features:posts:meeting:domain")
include(":features:posts:meeting:presentation")
include(":features:posts:member:data")
include(":features:posts:member:domain")
include(":features:posts:member:presentation")
include(":features:profile:data")
include(":features:profile:domain")
include(":features:profile:presentation:")
include(":features:setting:presentation")
