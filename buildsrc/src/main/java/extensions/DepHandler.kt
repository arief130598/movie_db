package extensions

import Deps
import Modules
import TestDeps
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.appModuleDeps() {
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.legacy)
    implementation(Deps.AndroidX.Constraint.constraintLayout)
    implementation(Deps.AndroidX.Coordinator.coordinatorLayout)
    implementation(Deps.Shimmer.shimmer)
    glide()
    lifecycle()
    navigation()
    room()
    koin()
    network()
    testing()
}
