plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.local"
}

dependencies {
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(project(":domain:entity"))
    implementation(project(":data:model"))
    implementation(project(":data:utils"))
}