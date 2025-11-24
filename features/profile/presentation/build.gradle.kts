plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.presentation.ui)
            implementation(projects.core.image)

            implementation(projects.features.profile.domain)

            implementation(libs.rich.editor)
            implementation(libs.rich.editor.coil3)
        }
    }
}
