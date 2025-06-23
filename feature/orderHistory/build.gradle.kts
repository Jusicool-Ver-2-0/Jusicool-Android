plugins {
    id("jusicool.android.feature")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.meister.orderhistory"
}

dependencies {
    implementation(project(":domain:usecase"))
    implementation(project(":domain:entity"))
    implementation(project(":ui:utils"))
    implementation("com.google.accompanist:accompanist-swiperefresh:0.31.2-alpha")
}