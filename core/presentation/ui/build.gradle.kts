plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            api(projects.core.presentation.designsystem)
        }
    }
}
