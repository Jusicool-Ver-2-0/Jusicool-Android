plugins {
    `kotlin-dsl`
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}



dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}


gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "jusicool.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidHilt") {
            id = "jusicool.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLint") {
            id = "jusicool.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }

        register("androidCore") {
            id = "jusicool.android.core"
            implementationClass = "AndroidCoreConventionPlugin"
        }

        register("androidCompose") {
            id = "jusicool.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("jvmLibrary") {
            id = "jusicool.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("androidFeature") {
            id = "jusicool.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}