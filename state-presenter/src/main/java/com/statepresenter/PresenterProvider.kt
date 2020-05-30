package com.statepresenter

import androidx.savedstate.SavedStateRegistry

inline fun <P: StatePresenter, reified S> providePresenterLazily(
    savedStateRegistry: SavedStateRegistry,
    crossinline presenterFactory: (stateSaver: StateSaver<S>) -> P
): Lazy<P> = lazy {
    presenterFactory(
        StateSaver(
            presenterKey = S::class.qualifiedName.toString(),
            savedStateRegistry = savedStateRegistry
        )
    )
}