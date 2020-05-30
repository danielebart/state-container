package com.statecontainer.sample

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.statecontainer.StateContainer
import com.statecontainer.StateSaver
import com.statecontainer.provideContainerLazily

class IncrementActivity : AppCompatActivity(R.layout.activity_increment), IncrementContract.View {

    private val incrementButton by lazy { findViewById<Button>(R.id.incrementButton) }
    private val container by provideContainerLazily<IncrementPresenter, Int>(savedStateRegistry) { stateSaver ->
        IncrementPresenter(stateSaver, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        incrementButton.setOnClickListener { container.onIncrementClick() }
        container.start()
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun displayCurrentIncrementValue(value: Int) {
        incrementButton.text = value.toString()
    }
}

interface IncrementContract {

    interface View {

        fun showToast(text: String)

        fun displayCurrentIncrementValue(value: Int)
    }

    interface Container : StateContainer {

        fun onIncrementClick()
    }
}

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