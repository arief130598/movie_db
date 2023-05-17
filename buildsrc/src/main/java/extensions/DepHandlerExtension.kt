package extensions

import Deps
import Modules
import TestDeps
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.lifecycle() {
    implementation(Deps.AndroidX.Lifecycle.runtime)
    implementation(Deps.AndroidX.Lifecycle.viewModel)
    implementation(Deps.AndroidX.Lifecycle.liveData)
    implementation(Deps.AndroidX.Lifecycle.common)
}

fun DependencyHandler.navigation() {
    implementation(Deps.AndroidX.Navigation.ui)
    implementation(Deps.AndroidX.Navigation.fragment)
    implementation(Deps.AndroidX.Navigation.commonKtx)
    implementation(Deps.AndroidX.Navigation.dynamicFeaturesFragment)
}

fun DependencyHandler.room() {
    kapt(Deps.AndroidX.Room.compiler)
    implementation(Deps.AndroidX.Room.ktx)
    implementation(Deps.AndroidX.Room.runtime)
}

fun DependencyHandler.glide() {
    implementation(Deps.Glide.glide)
    implementation(Deps.Glide.compiler)
}

fun DependencyHandler.koin() {
    implementation(Deps.Koin.koin_core)
    implementation(Deps.Koin.koin_android)
}

fun DependencyHandler.network() {
    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.moshiConverter)
    implementation(Deps.OkHttp.okhttp)
    implementation(Deps.OkHttp.logging)
}

fun DependencyHandler.testing() {
    testImplementation(TestDeps.JUnit.junit)
    testImplementation(TestDeps.AndroidX.roomTest)
    testImplementation(TestDeps.AndroidX.coreTesting)
    testImplementation(TestDeps.Coroutines.coroutines)
    testImplementation(TestDeps.Mockito.core)
    testImplementation(TestDeps.Mockito.android)
    testImplementation(TestDeps.Mockito.inline)
    testImplementation(TestDeps.MockitoKotlin.mockito)
    androidTestImplementation(TestDeps.JUnit.junit)
    androidTestImplementation(TestDeps.Espresso.espressoImplementation)
}