pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Jusicool-Android"
include(":app")

include(":domain")
include(":feature")

include(":ui")
include(":ui:design-system")
include(":ui:utils")

include(":data")
include(":data:network")
include(":data:di")
include(":data:local")
include(":data:model")
include(":data:repository")
include(":data:utils")
