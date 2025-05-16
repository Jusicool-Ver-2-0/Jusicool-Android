plugins {
    id("jusicool.android.feature")
    id("jusicool.android.hilt")
}

android {
    namespace = "com.meister.assets"
}

dependencies {

    implementation(project(":ui:utils"))
}