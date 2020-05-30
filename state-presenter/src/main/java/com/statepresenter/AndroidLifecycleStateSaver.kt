@file:Suppress("UNCHECKED_CAST")

package com.statepresenter

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.savedstate.SavedStateRegistry

internal class AndroidLifecycleStateSaver<S>(
    presenterKey: String,
    savedStateRegistry: SavedStateRegistry
) :
    StateSaver<S> {

    private var state: S? = null

    private val stateProvider = SavedStateRegistry.SavedStateProvider {
        bundleOf(presenterKey to state)
    }

    init {
        val restoredState: Bundle? = savedStateRegistry.consumeRestoredStateForKey(presenterKey)
        state = restoredState?.get(presenterKey) as S
        savedStateRegistry.registerSavedStateProvider(presenterKey, stateProvider)
    }

    override fun put(stateToSave: S) {
        state = stateToSave
    }

    override fun get(): S? = state
}