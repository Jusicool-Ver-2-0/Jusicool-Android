plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.di"
}

dependencies {
    implementation(libs.room.runtime)

    implementation(project(":data:repository"))
    implementation(project(":domain:repository"))
    implementation(project(":data:local"))
}