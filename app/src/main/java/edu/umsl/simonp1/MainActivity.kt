package edu.umsl.simonp1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {
    companion object {
        val LEVEL_KEY = "LEVEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        easyButton.setOnClickListener(easyListener)
        intermediateButton.setOnClickListener(intermediateListener)
        difficultButton.setOnClickListener(difficultListener)
        Log.e("mainActivity", "ACTIVITY IS BEING CREATED!!!!!")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "----> onDestroy")
    }

    private fun play(level: Level):Unit {
        val playIntent = Intent(this, GameActivity::class.java)
        playIntent.putExtra(LEVEL_KEY, level)
        this.startActivity(playIntent)
        finish()
    }

    private val easyListener = View.OnClickListener {
        Log.e("mainActivity", "easyListener")
        play(Level.EASY)
    }

    private val intermediateListener = View.OnClickListener {
        play(Level.INTERMEDIATE)
    }

    private val difficultListener = View.OnClickListener {
        play(Level.DIFFICULT)
    }
}
