import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

rootProject.name = "intellij-luau"

// The block with plugin versions is intentionally kept inside the gradle libs.versions since it's better.
// They moved it here to simplify the setup for newbies

plugins {
    // They added this back in 2.0.2, but I have no idea why.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("org.jetbrains.intellij.platform.settings") version "2.16.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    // Configure all projects' repositories
    repositories {
        mavenCentral()

        // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
        intellijPlatform {
            defaultRepositories()
        }
    }
}
