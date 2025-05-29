plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.repository"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(project(":data:model"))
    implementation(project(":data:utils"))
    implementation(project(":data:network"))
    implementation(project(":data:local"))
    implementation(project(":domain:repository"))
    implementation(project(":domain:entity"))
}