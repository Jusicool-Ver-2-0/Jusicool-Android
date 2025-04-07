plugins {
    id("jusicool.android.application")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.example.jusicool_android"


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    implementation(libs.app.update.ktx)
}