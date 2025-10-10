plugins {
    id("soomjae.multiplatform.core")
    id("soomjae.multiplatform.data.remote")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.remote)

            api(libs.bundles.filekit)
        }
    }
}
