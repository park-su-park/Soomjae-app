plugins {
    id("soomjae.multiplatform.ui")
    id("soomjae.build-config")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            implementation(projects.core.domain)
            api(projects.core.presentation.designsystem)

            implementation(libs.coil.network.ktor)
            implementation(libs.rich.editor)
            implementation(libs.rich.editor.coil3)
        }

        commonTest.dependencies {
            implementation(projects.core.test)
        }
    }
}
