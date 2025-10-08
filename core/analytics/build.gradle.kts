plugins {
    id("soomjae.multiplatform.ui")
    id("soomjae.build-config")
    id("soomjae.cocoapods.library")
    alias(libs.plugins.google.services)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
        }
    }
}
