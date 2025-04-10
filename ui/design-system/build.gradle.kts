plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}
android {
    namespace = "com.example.design_system"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
}
