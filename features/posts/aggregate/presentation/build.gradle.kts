plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.presentation.designsystem)

            implementation(projects.features.posts.aggregate.domain)

            implementation(projects.features.posts.community.presentation)
            implementation(projects.features.posts.meeting.presentation)
        }
    }
}
