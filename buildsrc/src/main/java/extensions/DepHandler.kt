package extensions

import Deps
import Modules
import TestDeps
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.coreModuleDeps() {
    implementation(Deps.AndroidX.coreKtx)
    hilt()
    network()
    room()
}

fun DependencyHandler.featureModuleDeps() {
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.legacy)
    implementation(Deps.AndroidX.Constraint.constraintLayout)
    implementation(Deps.AndroidX.Coordinator.coordinatorLayout)
    implementation(Deps.Shimmer.shimmer)
    glide()
    lifecycle()
    navigation()
    testing()
}
