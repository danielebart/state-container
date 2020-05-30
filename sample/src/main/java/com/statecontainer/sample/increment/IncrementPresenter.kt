package com.statecontainer.sample.increment

import com.statecontainer.StateContainer

class IncrementPresenter(
    private val stateContainer: StateContainer<Int>,
    private val view: IncrementContract.View
) : IncrementContract.Container {

    private val currentState
        get() = stateContainer.get() ?: 0

    fun start() {
        view.showToast("hello! current state is: $currentState")
        view.displayCurrentIncrementValue(currentState)
    }

    override fun onIncrementClick() {
        stateContainer.put(currentState + 1)
        view.displayCurrentIncrementValue(currentState)
    }
}