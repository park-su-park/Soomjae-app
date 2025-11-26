plugins {
    id("soomjae.multiplatform.ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.presentation.ui)
            implementation(projects.core.notification.domain)
        }
    }
}
