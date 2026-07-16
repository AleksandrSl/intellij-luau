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

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        bundledPlugins("tanvd.grazi".split(','))
        // This version determines the sinceBuild unless sinceBuild is not explicitly defined.
        intellijIdeaUltimate("2024.3")
        testFramework(TestFrameworkType.Platform)
    }
}

tasks {
    prepareSandbox {
        from(layout.projectDirectory.dir("/src/main/resources/typeDeclarations")) {
             into(pluginName.map { "$it/typeDeclarations" })
        }
    }
}
