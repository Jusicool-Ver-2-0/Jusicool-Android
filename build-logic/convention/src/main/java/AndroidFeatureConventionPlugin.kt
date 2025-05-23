import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.jusicool.convention.libs

// Plugin class to configure Android feature modules with core, Compose, and Hilt dependencies
class AndroidFeatureConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Apply necessary core, Compose, and Hilt plugins for Android
            with(pluginManager) {
                apply("jusicool.android.core")  // Apply Compose settings from custom plugin
                apply("jusicool.android.compose")
                apply("jusicool.android.hilt")  // Apply Hilt for dependency injection
            }

            // Add necessary dependencies from Version Catalog
            dependencies {
                add("implementation", project(":ui:design-system"))
                add("implementation", project(":data:model"))

                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())  // Add Lifecycle runtime for Compose
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())  // Add ViewModel support for Compose
                add("implementation", libs.findLibrary("kotlinx.datetime").get())  // Add KotlinX DateTime library
                add("implementation", libs.findLibrary("kotlinx.immutable").get())  // Add KotlinX Immutable collections library
            }
        }
    }
}
