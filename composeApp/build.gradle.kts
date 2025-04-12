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
            implementation(projects.core.presentation.designsystem)
        }
    }
}
