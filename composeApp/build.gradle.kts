plugins {
    id("soomjae.app")
    id("soomjae.build-config")
    id("soomjae.cocoapods.app")
}

dependencies {
    coreLibraryDesugaring(libs.desugaring)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.analytics)
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(projects.core.domain)
            implementation(projects.core.image)
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
        pod("GoogleSignIn") {
            version = libs.versions.cocoapods.google.sign.get()
            linkOnly = true
        }
    }
}
