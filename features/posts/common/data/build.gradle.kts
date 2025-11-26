plugins {
    id("soomjae.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.domain)
            api(projects.core.remote)

            api(projects.features.posts.common.domain)
        }
    }
}
