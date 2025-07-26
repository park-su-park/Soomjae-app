plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.presentation.designsystem)
            implementation(projects.core.presentation.ui)

            implementation(projects.features.posts.aggregate.domain)

            api(projects.features.posts.common.presentation)
            implementation(projects.features.posts.community.presentation)
            implementation(projects.features.posts.meeting.presentation)

            implementation(libs.rich.editor)
        }
    }
}
