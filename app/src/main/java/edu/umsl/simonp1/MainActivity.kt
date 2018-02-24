package edu.umsl.simonp1

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        easyButton.setOnClickListener(easyListener)
        intermediateButton.setOnClickListener(intermediateListener)
        difficultButton.setOnClickListener(difficultListener)

    }

    val easyListener = View.OnClickListener {
        println("easy")
    }
    private val intermediateListener = View.OnClickListener {
        println("intermediate")
    }

    private val difficultListener = View.OnClickListener {
        println("difficult")
    }
}
