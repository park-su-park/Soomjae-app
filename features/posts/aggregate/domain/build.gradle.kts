plugins {
    id("soomjae.multiplatform.core")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.domain)
            api(projects.features.posts.common.domain)
            api(projects.features.posts.community.domain)
            api(projects.features.posts.meeting.domain)
            api(projects.features.posts.member.domain)
        }
    }
}
