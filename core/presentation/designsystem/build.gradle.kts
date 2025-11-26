plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
        }
    }
}
