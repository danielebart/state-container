package com.statecontainer.sample.increment

import com.statecontainer.StateContainer

interface IncrementContract {

    interface View {

        fun showToast(text: String)

        fun displayCurrentIncrementValue(value: Int)
    }

    interface Container : StateContainer {

        fun onIncrementClick()
    }
}