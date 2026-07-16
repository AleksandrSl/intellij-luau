import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.changelog)
    alias(libs.plugins.serialization)

    id("org.jetbrains.intellij.platform")
}

sourceSets {
    main {
        java.srcDirs("src/main/gen")
    }
}


// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    implementation(libs.serialization)
    implementation(libs.bytesize)
    testImplementation(libs.junit)
    // Needed to resolve FileComparisonFailedError's supertype chain (it extends org.opentest4j.AssertionFailedError),
    // which test-framework doesn't declare as a transitive dependency.
    testImplementation("org.opentest4j:opentest4j:1.3.0")

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        bundledPlugins("tanvd.grazi".split(','))
        // This version determines the sinceBuild unless sinceBuild is not explicitly defined.
        intellijIdeaUltimate("2024.3")
        testFramework(TestFrameworkType.Platform)
    }
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }
    }
}

tasks {
    prepareSandbox {
        from(layout.projectDirectory.dir("/src/main/resources/typeDeclarations")) {
             into(pluginName.map { "$it/typeDeclarations" })
        }
    }
}
