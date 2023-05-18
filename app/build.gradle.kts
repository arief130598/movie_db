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
        applicationId = AndroidConfig.APPLICATION_ID
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(BuildConfigType.string, BuildConfigName.api, BuildConfigValue.movie_api)
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_11.toString()))
        }
    }
    buildFeatures {
        dataBinding = true
    }
    dynamicFeatures += setOf(
        DynamicFeature.home, DynamicFeature.detail, DynamicFeature.search, DynamicFeature.favorite
    )

    // Dokka Configuration
    tasks.dokkaHtml.configure {
        dokkaSourceSets{
            configureEach{
                includes.from("module.md")
                includeNonPublic.set(true)

                pluginsMapConfiguration.set(
                        mapOf("org.jetbrains.dokka.base.DokkaBase" to """{ "separateInheritedMembers": true}""")
                )
            }
        }
    }
}

dependencies {
    coreModuleDeps()
    featureModuleDeps()

    implementation(project(Modules.core))
    implementation(project(Modules.data))
    implementation(project(Modules.domain))
}