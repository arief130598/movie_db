import extensions.coreModuleDeps

plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
    id(Plugins.NAVIGATION_SAFE_ARGS)
    id(Plugins.DOKKA)
    id(Plugins.KOTLIN_PARCELIZE)
}

android {
    namespace = AndroidConfig.APPLICATION_ID
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK
    }
}

dependencies {
    coreModuleDeps()

    implementation(project(Modules.data))
}