import java.io.FileInputStream
import java.util.Properties

plugins {
    id("jusicool.android.core")
    id("jusicool.android.hilt")
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "BASE_URL",
            value = "\"${getApiKey("BASE_URL")}\""
        )
        buildConfigField(
            type = "String",
            name = "KOREAINVESTMENT_APP_SECRET",
            value = "\"${getApiKey("KOREAINVESTMENT_APP_SECRET")}\""
        )
        buildConfigField(
            type = "String",
            name = "KOREAINVESTMENT_API_KEY",
            value = "\"${getApiKey("KOREAINVESTMENT_API_KEY")}\""
        )
        buildConfigField(
            type = "String",
            name = "KOREAINVESTMENT_BASE_URL",
            value = "\"${getApiKey("KOREAINVESTMENT_BASE_URL")}\""
        )
        buildConfigField(
            type = "String",
            name = "NEIS_API_KEY",
            value = "\"${getApiKey("NEIS_API_KEY")}\""
        )
    }

    namespace = "com.jusicool.network"
}

dependencies {
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

fun getApiKey(propertyKey: String): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey) ?: throw IllegalArgumentException("Property $propertyKey no found in local.properties")
}