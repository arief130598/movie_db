import extensions.coreModuleDeps
import extensions.featureModuleDeps

plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.ANDROID)
    kotlin(Plugins.KAPT)
    id(Plugins.NAVIGATION_SAFE_ARGS)
    id(Plugins.DOKKA)
}

android {
    namespace = AndroidConfig.APPLICATION_ID
    compileSdk = AndroidConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_11.toString()))
        }
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    coreModuleDeps()
    featureModuleDeps()

    implementation(project(Modules.app))
    implementation(project(Modules.core))
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
}