package edu.umsl.simonp1

import android.animation.AnimatorInflater
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*

private const val GAME_FRAGMENT_TAG = "GameFramgment"
const val LEVEL = "Level"

class GameActivity : Activity(), GameFramgment.MainFragmentListener {


    var gameFramgment: GameFramgment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var level = intent.getSerializableExtra(MainActivity.LEVEL_KEY)
        val bundle = Bundle()
        bundle.putSerializable(LEVEL, level)

        val manager = fragmentManager
        gameFramgment = manager.findFragmentByTag(GAME_FRAGMENT_TAG) as? GameFramgment

        if (gameFramgment == null) {
            gameFramgment = GameFramgment()
            gameFramgment!!.arguments = bundle

            manager.beginTransaction()
                    .add(gameFramgment, GAME_FRAGMENT_TAG)
                    .commit()
        }


        gameFramgment?.listener = this


        blueButton.setOnClickListener(blueListener)
        greenButton.setOnClickListener(greenListener)
        redButton.setOnClickListener(redListener)
        yellowButton.setOnClickListener(yellowListener)
        startButton.setOnClickListener(startListener)

    }

    private val blueListener = View.OnClickListener {
        gameFramgment?.addToSequence(Colors.BLUE)
    }

    private val greenListener = View.OnClickListener {
        gameFramgment?.addToSequence(Colors.GREEN)
    }

    private val redListener = View.OnClickListener {
        gameFramgment?.addToSequence(Colors.RED)
    }

    private val yellowListener = View.OnClickListener {
        gameFramgment?.addToSequence(Colors.YELLOW)
    }

    private val startListener = View.OnClickListener {
        gameFramgment?.start()
    }

    override fun show(color: Colors) {
        Log.e("trig        ", "new cycle")
        val animator = AnimatorInflater.loadAnimator(this, R.animator.clickanimator)
//            animator.startDelay = (index  * 300).toLong()
        val view: View = when (color) {
            Colors.BLUE -> blueButton
            Colors.GREEN -> greenButton
            Colors.RED -> redButton
            else -> yellowButton
        }
        animator.setTarget(view)
        animator.start()
        Log.e("trig        ", color.toString())


        val e = Log.e("SEQUENCE", "I was triggered!")
    }
}
