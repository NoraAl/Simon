package edu.umsl.simonp1

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultView : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var result = intent.getSerializableExtra(RESULT_VIEW)

        resultText.text = "Score: " + result.toString()
    }
}
