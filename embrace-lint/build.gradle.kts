plugins {
    id("java-library")
    id("kotlin")
    id("com.android.lint")
}

import io.embrace.gradle.Versions

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly("com.android.tools.lint:lint-api:${Versions.lint}")

    testCompileOnly("com.android.tools.lint:lint-api:${Versions.lint}")
    testImplementation("com.android.tools.lint:lint-tests:${Versions.lint}")
    testImplementation("junit:junit:${Versions.junit}")
}
