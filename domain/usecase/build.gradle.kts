plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.usecase"
}

dependencies {
    implementation(project(":domain:repository"))
    implementation(project(":data:model"))
    implementation(project(":domain:entity"))
    implementation(project(":domain:utils"))
    implementation(project(":domain:utils"))
}