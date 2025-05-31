plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.presentation.ui)

            implementation(projects.features.post.domain)

            implementation(libs.rich.editor)
        }
    }
}
