plugins {
    id("soomjae.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.data)

            implementation(projects.features.posts.common.data)
            implementation(projects.features.posts.community.domain)
        }
    }
}
