plugins {
    id("soomjae.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.remote)
            implementation(projects.core.domain)

            implementation(projects.core.notification.domain)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)

            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.messaging)
        }
    }
}
