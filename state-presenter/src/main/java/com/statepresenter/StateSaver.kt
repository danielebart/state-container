@file:Suppress("UNCHECKED_CAST")

package com.statepresenter

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.savedstate.SavedStateRegistry

class StateSaver<S>(presenterKey: String, savedStateRegistry: SavedStateRegistry) {

    private var state: S? = null

    private val stateProvider = SavedStateRegistry.SavedStateProvider {
        bundleOf(presenterKey to state)
    }

    init {
        val restoredState: Bundle? = savedStateRegistry.consumeRestoredStateForKey(presenterKey)
        state = restoredState?.get(presenterKey) as S
        savedStateRegistry.registerSavedStateProvider(presenterKey, stateProvider)
    }

    fun put(stateToSave: S) {
        state = stateToSave
    }

    fun get(): S? = state
}