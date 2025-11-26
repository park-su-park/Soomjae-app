plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.posts.common.presentation)
            implementation(projects.features.posts.meeting.domain)

            implementation(libs.rich.editor)
        }
    }
}
