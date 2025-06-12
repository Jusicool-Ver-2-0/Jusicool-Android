plugins {
    id("jusicool.android.feature")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.investmentsearch"
}

dependencies {
    implementation(project(":domain:usecase"))
    implementation(project(":domain:entity"))
    implementation(project(":ui:utils"))
}