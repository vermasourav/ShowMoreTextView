package com.verma.android.widgets.text.showmore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.verma.android.widgets.text.showmore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.readMoreTextView.setStateChangeListener { state ->
            Log.d(
                TAG, "onClicked:$state"
            )
        }
        binding.readMoreTextView.updateText(getString(R.string.more_text), false)

    }

}