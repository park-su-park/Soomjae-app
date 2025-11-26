plugins {
    id("soomjae.multiplatform.core.test")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)

            implementation(libs.arrow)
            implementation(libs.koin.core)
            implementation(libs.koin.test.get().toString()) {
                exclude(group = "junit", module = "junit")
            }
        }
    }
}
