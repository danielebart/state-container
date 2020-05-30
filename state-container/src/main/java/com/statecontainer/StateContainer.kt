package com.statecontainer

/**
 * Allows to save a object state and retrieve its value after any Android configuration change
 * or process death. Due to platform limitations, the state object MUST be a "bundable" object,
 * this means that the state should be a primitive, Parcelable, Serializable or any object which
 * can be added to a Bundle object.
 */
interface StateContainer<S> {

    /**
     * Put in the StateSaver a new state to save.
     */
    fun put(stateToSave: S)

    /**
     * Return the current saved state or null if no state was saved before.
     */
    fun get(): S?
}