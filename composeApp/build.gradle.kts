plugins {
    id("soomjae.app")
    id("soomjae.build-config")
    alias(libs.plugins.kotlin.cocoapods)
}

dependencies {
    coreLibraryDesugaring(libs.desugaring)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(projects.core.domain)
            implementation(projects.core.presentation.designsystem)
            implementation(projects.core.presentation.ui)
            implementation(projects.core.remote)

            implementation(projects.features.auth.data)
            implementation(projects.features.auth.presentation)
            implementation(projects.features.home.presentation)
            implementation(projects.features.posts.aggregate.data)
            implementation(projects.features.posts.aggregate.presentation)
            implementation(projects.features.profile.presentation)
            implementation(projects.features.setting.presentation)
        }
    }

    cocoapods {
        version = "1.0"
        summary = "Kotlin project with CocoaPods Compose dependencies"
        homepage = "https://github.com/park-su-park/SoomJae-app"

        podfile = project.file("../iosApp/Podfile")

        ios.deploymentTarget = "16.6"

        pod("GoogleSignIn") {
            version = libs.versions.cocoapods.google.sign.get()
            linkOnly = true
        }

        framework {
            baseName = "composeApp"
            isStatic = true
        }
    }
}
