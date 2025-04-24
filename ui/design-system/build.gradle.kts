plugins {
    id("jusicool.android.core")
    id("jusicool.android.lint")
    id("jusicool.android.compose")
}
android {
    namespace = "com.jusicool.design_system"
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.androidx.ui.android)
}
