plugins {
    id("soomjae.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.posts.common.data)
            implementation(projects.features.posts.meeting.domain)
        }
    }
}
