plugins {
    id("soomjae.multiplatform.core")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.posts.common.domain)
        }
    }
}
