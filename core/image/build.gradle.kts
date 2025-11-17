plugins {
    id("soomjae.multiplatform.data.remote")
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.remote)
            implementation(projects.core.domain)
            implementation(projects.core.presentation.ui)

            api(libs.bundles.filekit)
        }
    }
}
