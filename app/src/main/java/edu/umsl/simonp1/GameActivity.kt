package edu.umsl.simonp1

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : Activity() {
    var singleGame: GameModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var level = intent.getSerializableExtra(MainActivity.LEVEL_KEY)
        singleGame = GameModel(level as Level?)
        Log.e("Error", "mmmmm")

    }
}
