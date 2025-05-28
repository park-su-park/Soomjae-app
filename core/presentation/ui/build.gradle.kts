plugins {
    id("soomjae.multiplatform.ui")
    id("soomjae.build-config")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.domain)
            api(projects.core.presentation.designsystem)

            implementation(libs.coil.network.ktor)
        }
    }
}
