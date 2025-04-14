plugins {
    id("soomjae.multiplatform.data.remote")
    id("soomjae.build-config")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
        }
    }
}
