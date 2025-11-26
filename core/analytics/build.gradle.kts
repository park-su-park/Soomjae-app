plugins {
    id("soomjae.multiplatform.ui")
    id("soomjae.build-config")
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
