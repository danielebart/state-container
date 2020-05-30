@file:Suppress("UNCHECKED_CAST")

package com.statecontainer

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.savedstate.SavedStateRegistry

internal class AndroidLifecycleStateContainer<S>(
    containerKey: String,
    savedStateRegistry: SavedStateRegistry
) : StateContainer<S> {

    private var state: S? = null
    private val stateProvider = SavedStateRegistry.SavedStateProvider {
        bundleOf(containerKey to state)
    }

    init {
        val restoredState: Bundle? = savedStateRegistry.consumeRestoredStateForKey(containerKey)
        state = restoredState?.get(containerKey) as S
        savedStateRegistry.registerSavedStateProvider(containerKey, stateProvider)
    }

    override fun put(stateToSave: S) {
        state = stateToSave
    }

    override fun get(): S? = state
}