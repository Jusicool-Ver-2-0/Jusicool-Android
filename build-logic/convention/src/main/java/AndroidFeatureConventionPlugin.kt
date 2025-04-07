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
                apply("sweat.android.core")  // Apply core Android settings from custom plugin
                apply("sweat.android.compose")  // Apply Compose settings from custom plugin
                apply("sweat.android.hilt")  // Apply Hilt for dependency injection
            }

            // Add necessary dependencies from Version Catalog
            dependencies {
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())  // Add Lifecycle runtime for Compose
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())  // Add ViewModel support for Compose
                add("implementation", libs.findLibrary("kotlinx.datetime").get())  // Add KotlinX DateTime library
                add("implementation", libs.findLibrary("kotlinx.immutable").get())  // Add KotlinX Immutable collections library
            }
        }
    }
}
