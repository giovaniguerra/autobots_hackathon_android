plugins {
    id("com.android.application") version "4.0.2" apply false
    id("com.android.library") version "4.0.2" apply false
    kotlin("android") version "1.4.21" apply false
    kotlin("plugin.serialization") version "1.4.21" apply false
    id("io.gitlab.arturbosch.detekt") version "1.12.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
    id("com.github.ben-manes.versions") version "0.29.0"
}

allprojects {
    group = "br.com.gok.digital.autobotshackatonandroid"
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.4")
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    ktlint {
        debug.set(false)
        version.set("0.38.1")
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

// tasks.withType<DependencyUpdatesTask> {
//    rejectVersionIf {
//        isNonStable(candidate.version)
//    }
// }

fun isNonStable(version: String) = "^[0-9,.v-]+(-r)?$".toRegex().matches(version).not()