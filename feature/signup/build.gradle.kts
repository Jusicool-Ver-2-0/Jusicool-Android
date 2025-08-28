plugins {
    id("jusicool.android.feature")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.signup"
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    implementation(project(":domain:usecase"))
    implementation(project(":domain:entity"))
    implementation(project(":ui:utils"))
}