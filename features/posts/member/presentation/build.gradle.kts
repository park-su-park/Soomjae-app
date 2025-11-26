plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.image)
            implementation(projects.features.posts.common.presentation)
            implementation(projects.features.posts.member.domain)

            implementation(libs.rich.editor)
            implementation(libs.rich.editor.coil3)
        }
    }
}
