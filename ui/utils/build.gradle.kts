plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}
android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.jusicool.utils"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
}
