plugins {
    id("embrace-prod-defaults")
}

description = "Embrace Android SDK: OkHttp3"

android {
    namespace = "io.embrace.android.embracesdk.okhttp3"
}

dependencies {
    compileOnly(libs.okhttp)
    compileOnly(project(":embrace-android-core"))
    compileOnly(project(":embrace-android-infra"))
    compileOnly(project(":embrace-android-sdk"))
    testImplementation(project(":embrace-android-core"))
    testImplementation(project(":embrace-android-infra"))
    testImplementation(project(":embrace-android-sdk"))
}
