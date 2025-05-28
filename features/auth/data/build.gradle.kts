plugins {
    id("soomjae.multiplatform.data.preferences")
    id("soomjae.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.remote)

            implementation(projects.features.auth.domain)
        }
    }
}
