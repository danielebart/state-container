package com.statecontainer.sample.increment

interface IncrementContract {

    interface View {

        fun showToast(text: String)

        fun displayCurrentIncrementValue(value: Int)
    }

    interface Container {

        fun onIncrementClick()
    }
}