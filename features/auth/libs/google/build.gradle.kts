import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    id("soomjae.multiplatform.ui")
    id("soomjae.build-config")
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.google.id)
        }

        commonMain.dependencies {
            implementation(projects.core.domain)
        }

        desktopMain.dependencies {
            implementation(libs.bundles.ktor.server)
            implementation(libs.auth0.jwt)
        }
    }

    cocoapods {
        summary = "Kotlin sample project with CocoaPods Compose dependencies"
        ios.deploymentTarget = "16.6"

        noPodspec()

        pod("GoogleSignIn") {
            version = libs.versions.cocoapods.google.sign.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}
