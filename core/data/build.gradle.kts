plugins {
    id("soomjae.multiplatform.data.preferences")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
        }
    }
}
