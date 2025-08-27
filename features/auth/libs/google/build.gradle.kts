plugins {
    id("soomjae.multiplatform.ui")
    id("soomjae.build-config")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services.auth)
            implementation(libs.google.id)
        }

        commonMain.dependencies {
            implementation(projects.core.domain)
        }
    }
}
