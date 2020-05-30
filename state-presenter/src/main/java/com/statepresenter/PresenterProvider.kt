package com.statepresenter

import androidx.savedstate.SavedStateRegistry

/**
 * Provides in a lazy-way a [StatePresenter] from a factory lambda and a [SavedStateRegistry].
 *
 * @param savedStateRegistry the [SavedStateRegistry] used to save the state. This object can be
 * easily obtained from any SavedStateRegistryOwner (Fragments, ComponentActivity, ...).
 * @param presenterFactory a [StatePresenter] should be provided through this lambda.
 */
inline fun <P : StatePresenter, reified S> providePresenterLazily(
    savedStateRegistry: SavedStateRegistry,
    noinline presenterFactory: (stateSaver: StateSaver<S>) -> P
): Lazy<P> = providePresenterLazily(S::class.java, savedStateRegistry, presenterFactory)

/**
 * Provides in a lazy-way a [StatePresenter] from a factory lambda and a [SavedStateRegistry].
 *
 * @param stateClass the state class.
 * @param savedStateRegistry the [SavedStateRegistry] used to save the state. This object can be
 * easily obtained from any SavedStateRegistryOwner (Fragments, ComponentActivity, ...).
 * @param presenterFactory a [StatePresenter] should be provided through this lambda.
 */
fun <P : StatePresenter, S> providePresenterLazily(
    stateClass: Class<S>,
    savedStateRegistry: SavedStateRegistry,
    presenterFactory: (stateSaver: StateSaver<S>) -> P
): Lazy<P> = lazy {
    presenterFactory(
        AndroidLifecycleStateSaver(
            presenterKey = stateClass.name,
            savedStateRegistry = savedStateRegistry
        )
    )
}