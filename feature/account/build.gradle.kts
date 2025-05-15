plugins {
    id("jusicool.android.feature")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.jusicool.account"
}

dependencies {
    implementation(project(":domain:usecase"))
    implementation(project(":domain:entity"))

}