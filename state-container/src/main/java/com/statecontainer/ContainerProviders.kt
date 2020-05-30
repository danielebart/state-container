package com.statecontainer

import androidx.savedstate.SavedStateRegistryOwner

/**
 * Returns a [StateContainer] from a [SavedStateRegistryOwner] receiver.
 */
inline fun <reified S> SavedStateRegistryOwner.createStateContainer(): StateContainer<S> =
    createStateContainer(S::class.java)

/**
 * Returns a [StateContainer] from a [SavedStateRegistryOwner] receiver and the state java class.
 */
fun <S> SavedStateRegistryOwner.createStateContainer(stateClass: Class<S>): StateContainer<S> =
    AndroidLifecycleStateContainer(
        containerKey = stateClass.name,
        savedStateRegistry = savedStateRegistry
    )
