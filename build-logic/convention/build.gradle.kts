plugins {
    `kotlin-dsl`
    kotlin("plugin.serialization")
}

group = "com.parksupark.soomjae.buildlogic"

dependencies {
    implementation(libs.bundles.build.config)
    implementation(libs.bundles.gradle)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("app") {
            id = "soomjae.app"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.multiplatform.SoomjaeAppConventionPlugin"
        }
        register("build-config") {
            id = "soomjae.build-config"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.common.BuildConfigConventionPlugin"
        }
        register("detekt") {
            id = "soomjae.detekt"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.analysis.DetektConventionPlugin"
        }
        register("multiplatform-core") {
            id = "soomjae.multiplatform.core"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.multiplatform.core.MultiplatformCoreConventionPlugin"
        }
        register("multiplatform-core-test") {
            id = "soomjae.multiplatform.core.test"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.multiplatform.core.MultiplatformCoreTestConventionPlugin"
        }
        register("multiplatform-data-preferences") {
            id = "soomjae.multiplatform.data.preferences"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.multiplatform.data.MultiplatformDataPreferencesConventionPlugin"
        }
        register("multiplatform-data-remote") {
            id = "soomjae.multiplatform.data.remote"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.multiplatform.data.MultiplatformDataRemoteConventionPlugin"
        }
        register("multiplatform-compose") {
            id = "soomjae.multiplatform.compose"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.multiplatform.ui.MultiplatformComposeConventionPlugin"
        }
        register("multiplatform-ui") {
            id = "soomjae.multiplatform.ui"
            implementationClass =
                "com.parksupark.soomjae.buildlogic.multiplatform.ui.MultiplatformUiConventionPlugin"
        }
    }
}
