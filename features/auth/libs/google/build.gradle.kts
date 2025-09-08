plugins {
    id("soomjae.multiplatform.ui")
    id("soomjae.build-config")
    id("soomjae.cocoapods.library")
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
        summary = "CocoaPods Library for GoogleSignIn"

        pod("GoogleSignIn") {
            version = libs.versions.cocoapods.google.sign.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}
