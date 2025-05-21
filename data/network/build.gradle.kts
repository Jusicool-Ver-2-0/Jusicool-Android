import java.io.FileInputStream
import java.util.Properties

plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}
android {
    namespace = "com.jusicool.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "BASE_URL",
            getApiKey("BASE_URL")
        )
    }
}

dependencies {
    debugImplementation(libs.debug.chuck)
    releaseImplementation(libs.release.chuck)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)

    implementation(project(":data:model"))
    implementation(project(":data:utils"))
}

fun getApiKey(propertyKey: String) : String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey) ?: throw IllegalArgumentException("Property $propertyKey not found in local.properties")
}