plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.presentation.designsystem)
            implementation(projects.core.presentation.ui)

            implementation(projects.features.posts.aggregate.domain)

            implementation(projects.features.posts.common.presentation)
            api(projects.features.posts.community.presentation)
            api(projects.features.posts.meeting.presentation)

            implementation(libs.rich.editor)
        }
    }
}
