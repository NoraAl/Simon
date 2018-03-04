package edu.umsl.simonp1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var result = intent.getSerializableExtra(RESULT_VIEW)

        resultText.text = "Score: " + result.toString()
        restartButton.setOnClickListener(restartListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("ResultView", "-------------> onDestroy")
    }

    private val restartListener = View.OnClickListener {
        val playIntent = Intent(this, MainActivity::class.java)
        this.startActivity(playIntent)
        finish()
    }
}
