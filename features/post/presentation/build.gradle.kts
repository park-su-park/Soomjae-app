plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.presentation.ui)

            implementation(projects.features.post.domain)
        }
    }
}
