plugins {
    id("soomjae.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.posts.aggregate.domain)

            implementation(projects.features.posts.common.data)
            implementation(projects.features.posts.community.data)
            implementation(projects.features.posts.meeting.data)
            implementation(projects.features.posts.member.data)
        }
    }
}
