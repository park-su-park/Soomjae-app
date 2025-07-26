plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.domain)
            api(projects.core.presentation.ui)

            api(projects.features.posts.common.domain)

            implementation(libs.rich.editor)
        }
    }
}
