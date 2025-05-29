plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.model"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))
    implementation(project(":domain:entity"))

    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)
}