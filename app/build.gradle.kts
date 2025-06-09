plugins {
    id("jusicool.android.application")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.jusicool_android"


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(project(":data:di"))
    implementation(project(":data:local"))
    implementation(project(":data:model"))
    implementation(project(":data:network"))
    implementation(project(":data:repository"))
    implementation(project(":data:datautils"))

    implementation(project(":domain:entity"))
    implementation(project(":domain:repository"))
    implementation(project(":domain:usecase"))
    implementation(project(":domain:domainutils"))

    implementation(project(":ui:design-system"))
    implementation(project(":ui:datautils"))

    implementation(project(":feature:chart"))
    implementation(project(":feature:account"))
    implementation(project(":feature:signin"))

    implementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    implementation(libs.app.update.ktx)
}