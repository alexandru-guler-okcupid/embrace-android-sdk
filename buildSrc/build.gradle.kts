import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())

    // Version of Kotlin used at build time
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")

    // NOTE: when updating any of these keep in sync with buildSrc/src/main/kotlin/io/embrace/gradle/Versions.kt
    implementation("com.android.tools.build:gradle:8.5.2")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.6")
    implementation("org.jetbrains.kotlinx:binary-compatibility-validator:0.16.3")
    implementation("org.jetbrains.kotlinx:kover-gradle-plugin:0.8.3")
}

// ensure the Kotlin + Java compilers both use the same language level.
project.tasks.withType(JavaCompile::class.java).configureEach {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}

// ensure the Kotlin + Java compilers both use the same language level.
project.tasks.withType(KotlinCompile::class.java).configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
