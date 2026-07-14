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
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation = configurations.getByName("integrationTestImplementation") {
    extendsFrom(configurations.testImplementation.get())
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
dependencies {
    implementation(libs.serialization)
    implementation(libs.bytesize)
    testImplementation(libs.junit)

    integrationTestImplementation(libs.junit.jupiter)
    "integrationTestRuntimeOnly"("org.junit.platform:junit-platform-launcher")
    integrationTestImplementation("org.jetbrains.kotlin:kotlin-stdlib:2.3.20-RC2")
    integrationTestImplementation(libs.kodein.di)
    integrationTestImplementation(libs.kotlinx.coroutines)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        // This version determines the sinceBuild unless sinceBuild is not explicitly defined.
        intellijIdeaUltimate("2024.3")
        testFramework(TestFrameworkType.Platform)
        testFramework(TestFrameworkType.Starter, configurationName = "integrationTestImplementation")
    }
}

tasks {
    prepareSandbox {
        from(layout.projectDirectory.dir("/src/main/resources/typeDeclarations")) {
            into(intellijPlatform.projectName.map { "$it/typeDeclarations" })
        }
    }
}

val integrationTest = intellijPlatformTesting.testIdeUi.register("integrationTest") {
    task {
        val integrationTestSourceSet = sourceSets.getByName("integrationTest")
        testClassesDirs = integrationTestSourceSet.output.classesDirs
        classpath = integrationTestSourceSet.runtimeClasspath
        useJUnitPlatform()
    }
}
