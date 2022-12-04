import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.4"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.21"
}

repositories {
    mavenCentral()
}

tasks {

    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}


tasks.withType < KotlinCompile > {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.languageVersion = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
}

allOpen {
    annotation("Benchmark")
}
benchmark {
    targets {
        register("main")
    }

}