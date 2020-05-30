package com.statecontainer.sample.increment

import com.statecontainer.StateSaver

class IncrementPresenter(
    private val stateSaver: StateSaver<Int>,
    private val view: IncrementContract.View
) : IncrementContract.Container {

    private val currentState
        get() = stateSaver.get() ?: 0

    fun start() {
        view.showToast("hello! current state is: $currentState")
        view.displayCurrentIncrementValue(currentState)
    }

    override fun onIncrementClick() {
        stateSaver.put(currentState + 1)
        view.displayCurrentIncrementValue(currentState)
    }
}