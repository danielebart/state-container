package com.statecontainer

import androidx.savedstate.SavedStateRegistry

/**
 * Provides in a lazy-way a [StateContainer] from a factory lambda and a [SavedStateRegistry].
 *
 * @param savedStateRegistry the [SavedStateRegistry] used to save the state. This object can be
 * easily obtained from any SavedStateRegistryOwner (Fragments, ComponentActivity, ...).
 * @param containerFactory a [StateContainer] should be provided through this lambda.
 */
inline fun <P : StateContainer, reified S> provideContainerLazily(
    savedStateRegistry: SavedStateRegistry,
    noinline containerFactory: (stateSaver: StateSaver<S>) -> P
): Lazy<P> = provideContainerLazily(S::class.java, savedStateRegistry, containerFactory)

/**
 * Provides in a lazy-way a [StateContainer] from a factory lambda and a [SavedStateRegistry].
 *
 * @param stateClass the state class.
 * @param savedStateRegistry the [SavedStateRegistry] used to save the state. This object can be
 * easily obtained from any SavedStateRegistryOwner (Fragments, ComponentActivity, ...).
 * @param containerFactory a [StateContainer] should be provided through this lambda.
 */
fun <P : StateContainer, S> provideContainerLazily(
    stateClass: Class<S>,
    savedStateRegistry: SavedStateRegistry,
    containerFactory: (stateSaver: StateSaver<S>) -> P
): Lazy<P> = lazy {
    containerFactory(
        AndroidLifecycleStateSaver(
            containerKey = stateClass.name,
            savedStateRegistry = savedStateRegistry
        )
    )
}