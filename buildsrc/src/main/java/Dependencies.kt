object Analysis {
    const val ktlint = "0.43.0"
}

object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.3.0"

    object Android {
        const val material = "com.google.android.material:material:1.8.0"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.5.1"
        const val palette = "androidx.palette:palette:1.0.0"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val legacy = "androidx.legacy:legacy-support-v4:1.0.0"

        object Activity {
            const val version = "1.4.0"
            const val activityKtx = "androidx.activity:activity-ktx:$version"
        }

        object Fragment {
            const val version = "1.4.1"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
            const val fragmentTesting = "androidx.fragment:fragment-testing:$version"
        }

        object Constraint {
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        }

        object Coordinator {
            const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.2.0"
        }

        object RecyclerView {
            const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        }

        object Lifecycle {
            private const val version = "2.5.1"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
        }

        object Navigation {
            private const val version = "2.5.1"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val commonKtx = "androidx.navigation:navigation-common-ktx:$version"
            const val dynamicFeaturesFragment =
                "androidx.navigation:navigation-dynamic-features-fragment:$version"
        }

        object Work {
            private const val version = "2.7.1"
            const val runtime = "androidx.work:work-runtime:$version"
        }

        object Room {
            private const val version = "2.5.0"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }
    }

    object Kotlin {
        private const val version = "1.8.0"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.6.4"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Shimmer {
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
    }

    object Dagger {
        private const val version = "2.44"
        const val hiltAndroid = "com.google.dagger:hilt-android:$version"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-compiler:$version"
    }

    object OkHttp {
        private const val version = "4.7.2"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val logging = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.6.2"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Glide {
        private const val version = "4.11.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Firebase {
        private const val version = "4.14.2"
        const val bom = "com.google.firebase:firebase-bom:31.2.0:$version"
        const val auth = "com.google.firebase:firebase-auth-ktx"
        const val firestore = "com.google.firebase:firebase-firestore-ktx"
        const val cloud_message = "com.google.firebase:firebase-messaging-ktx"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val perf = "com.google.firebase:firebase-perf-ktx"
        const val perf_plugin = "com.google.firebase:perf-plugin"
        const val dynamic_module = "com.google.firebase:firebase-dynamic-module-support"
    }

    object Koin {
        const val koin_core = "io.insert-koin:koin-core:3.2.2"
        const val koin_android = "io.insert-koin:koin-android:3.3.0"
    }
}

object TestDeps {
    object AndroidX {
        private const val version = "1.4.0"

        // AndroidX Test - JVM Testing
        const val coreKtx = "androidx.test:core-ktx:$version"
        const val rules = "androidx.test:rules:$version"
        const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val androidX_jUnit = "androidx.test.ext:junit-ktx:1.1.3"
        const val roomTest = "androidx.room:room-testing:2.4.3"
        const val navigationTest =
            "androidx.navigation:navigation-testing:2.4.2"
    }

    object Coroutines {
        private const val version = "1.6.1"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
        const val junitImplementation = "androidx.test.ext:junit:1.1.3"
    }

    object MockWebServer {
        private const val version = "4.9.3"
        const val mockwebserver = "com.squareup.okhttp3:mockwebserver:$version"
        const val okhttpIdlingResource = "com.jakewharton.espresso:okhttp3-idling-resource:1.0.0"
    }

    object MockK {
        const val mockK = "io.mockk:mockk:1.10.0"
    }

    object Mockito {
        private const val version = "5.0.0"
        const val core = "org.mockito:mockito-core:$version"
        const val inline = "org.mockito:mockito-inline:$version"
        const val android = "org.mockito:mockito-android:$version"
    }

    object MockitoKotlin {
        private const val version = "2.2.0"
        const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:$version"
    }

    object RoboElectric {
        private const val version = "4.9.2"
        const val robolectric = "org.robolectric:robolectric:$version"
    }

    object Turbine {
        private const val version = "0.12.1"
        const val turbine = "app.cash.turbine:turbine:$version"
    }

    object Espresso {
        private const val version = "3.4.0"
        const val espressoImplementation = "androidx.test.espresso:espresso-core:$version"
    }

    object Truth {
        private const val version = "1.0.1"
        const val truth = "com.google.truth:truth:1.0.1"
    }
}
