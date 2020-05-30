package com.statepresenter

import androidx.savedstate.SavedStateRegistry

/**
 * Provides in a lazy-way a [StatePresenter] from a factory lambda and a [SavedStateRegistry].
 *
 * @param savedStateRegistry the [SavedStateRegistry] used to save the state. This object can be
 * easily obtained from any SavedStateRegistryOwner (Fragments, ComponentActivity, ...).
 * @param presenterFactory a [StatePresenter] should be provided through this lambda.
 */
inline fun <P: StatePresenter, reified S> providePresenterLazily(
    savedStateRegistry: SavedStateRegistry,
    crossinline presenterFactory: (stateSaver: AndroidLifecycleStateSaver<S>) -> P
): Lazy<P> = lazy {
    presenterFactory(
        AndroidLifecycleStateSaver(
            presenterKey = S::class.qualifiedName.toString(),
            savedStateRegistry = savedStateRegistry
        )
    )
}