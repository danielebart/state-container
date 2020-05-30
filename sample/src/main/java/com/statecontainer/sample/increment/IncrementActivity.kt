package com.statecontainer.sample.increment

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.statecontainer.provideContainerLazily
import com.statecontainer.sample.R

class IncrementActivity : AppCompatActivity(R.layout.activity_increment),
    IncrementContract.View {

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
