plugins {
    id("soomjae.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.data)
            implementation(projects.core.remote)
            implementation(projects.core.domain)
            implementation(projects.features.profile.domain)
        }
    }
}
