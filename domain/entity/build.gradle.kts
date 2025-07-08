plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.entity"
}

dependencies {
    implementation(project(":domain:utils"))
}