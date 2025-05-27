plugins {
    id("soomjae.app")
    id("soomjae.build-config")
}

dependencies {
    coreLibraryDesugaring(libs.desugaring)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.domain)
            implementation(projects.core.presentation.designsystem)
            implementation(projects.core.presentation.ui)
            implementation(projects.core.remote)

            implementation(projects.features.auth.presentation)
            implementation(projects.features.home.presentation)
            implementation(projects.features.profile.presentation)
        }
    }
}
