package edu.umsl.simonp1

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_game.*
import java.io.Serializable


private const val GAME_FRAGMENT = "GameFramgment"
const val LEVEL = "Level"
const val RESULT = "RESULT"
const val RECORD = "RECORD"

@Suppress("DEPRECATION")
class GameActivity : Activity(), GameFramgment.MainFragmentListener {
    var gameFramgment: GameFramgment? = null
    var record: Int = 0
    var clickable = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var level = intent.getSerializableExtra(MainActivity.LEVEL_KEY)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        // read record
        record = sharedPref.getInt(getString(R.string.SAVE_SCORE), 0)


        titleTextView.text = "PLAYING " + level.toString() + " LEVEL"

        fragmentSetup(level)

        blueButton.setOnClickListener(blueListener)
        greenButton.setOnClickListener(greenListener)
        redButton.setOnClickListener(redListener)
        yellowButton.setOnClickListener(yellowListener)
        startButton.setOnClickListener(startListener)
    }

    private fun animateView(view: TextView, string: String) {
        view.text = string
        val anim = ObjectAnimator.ofFloat(view, "Alpha", 0f, 1f)
        anim.repeatMode = 1
        anim.duration = 1000
        // anim.repeatMode = Animation.ZORDER_TOP
        anim.start()

        val handler = Handler()

        handler!!.postDelayed({
            view.text = ""
        }, 1200)
    }

    private fun fragmentSetup(level: Serializable) {
        val bundle = Bundle()
        bundle.putSerializable(LEVEL, level)

        val manager = fragmentManager
        gameFramgment = manager.findFragmentByTag(GAME_FRAGMENT) as? GameFramgment

        if (gameFramgment == null) {
            gameFramgment = GameFramgment()
            gameFramgment!!.arguments = bundle
            manager.beginTransaction()
                    .add(gameFramgment, GAME_FRAGMENT)
                    .commit()
        }
        gameFramgment?.listener = this
    }

    private val blueListener = View.OnClickListener {
        if (clickable) {
            animate(blueButton)
            this.check(Colors.BLUE)
        }
    }

    private val greenListener = View.OnClickListener {
        if (clickable) {
            animate(greenButton)
            check(Colors.GREEN)
        }
    }

    private val redListener = View.OnClickListener {
        if (clickable) {
            animate(redButton)
            check(Colors.RED)
        }
    }

    private val yellowListener = View.OnClickListener {
        if (clickable) {
            animate(yellowButton)
            check(Colors.YELLOW)
        }
    }

    private val startListener = View.OnClickListener {
        startButton.isClickable = false
        startButton.visibility = View.INVISIBLE
        gameFramgment?.proceed(true)
    }

    private fun check(color: Colors) {

        val result = gameFramgment?.check(color)

        when (result?.result) {
            Status.CONTINUE -> titleTextView.text = "Current Score:" + result.currentScore.toString()
            Status.COMPLETED -> completed(result)
            else -> gameOver(result?.currentScore!!)
        }
    }

    private fun completed(result: GameModel.Triple) {
        titleTextView.text = "Current Score:" + result.currentScore.toString()
        animateView(this.messageText, "Correct!")

        val handler = Handler()
        handler!!.postDelayed({
            gameFramgment?.proceed()
        },
                1500
        )
    }

    private fun gameOver(currentScore: Int) {
        val color = gameFramgment?.getSequence()?.get(currentScore)
        val button = getButton(color)
        animateView(messageText, "The correct was " + color.toString())
        val handler = Handler()
        handler.postDelayed({
            animate(button, 1000, 1)
        }, 200)

        handler.postDelayed({
            val resultIntent = Intent(this, ResultView::class.java)
            resultIntent.putExtra(RESULT, currentScore)
            startActivity(resultIntent)
            finish()
        }, 2200)

    }

    private fun getButton(color: Colors?): Button {
        return when (color) {
            Colors.BLUE -> blueButton
            Colors.GREEN -> greenButton
            Colors.RED -> redButton
            else -> yellowButton
        }
    }


    override fun show(sequence: ArrayList<Colors>, duration: Long) {

        for (i in 0 until sequence.size) {
            val view = getButton(sequence[i])
            animate(view, duration, i)
        }
    }

    private fun animate(view: Button, duration: Long = 600, index: Int = 0) {
        val anim = ObjectAnimator.ofFloat(view, "Alpha", 0f, 1f)
        anim.repeatMode = 1
        anim.repeatMode = Animation.REVERSE

        val elevationAnim = ObjectAnimator.ofFloat(view, "Elevation", 10f)
        elevationAnim.repeatCount = 1
        elevationAnim.repeatMode = Animation.REVERSE

        val animSet = AnimatorSet()
        animSet.duration = duration
        animSet.startDelay = (index * duration)
        animSet.play(elevationAnim).with(anim);
        animSet.start();
    }

    override fun freeze() {
        clickable = false
    }

    override fun unfreeze() {
        clickable = true
    }

}

