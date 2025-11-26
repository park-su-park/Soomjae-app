plugins {
    id("soomjae.multiplatform.core")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.domain)
        }
    }
}
