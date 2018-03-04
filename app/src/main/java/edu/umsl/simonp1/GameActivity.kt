package edu.umsl.simonp1

import android.app.Activity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import android.graphics.PorterDuff
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.widget.ImageButton
import java.io.Serializable


private const val GAME_FRAGMENT = "GameFramgment"
const val LEVEL = "Level"
const val RESULT_VIEW = "RESULT_VIEW"

@Suppress("DEPRECATION")
class GameActivity : Activity(), GameFramgment.MainFragmentListener {


    var gameFramgment: GameFramgment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var level = intent.getSerializableExtra(MainActivity.LEVEL_KEY)

        titleTextView.text = "PLAYING "+ level.toString() + " LEVEL"

        fragmentSetup(level)

        blueButton.setOnClickListener(blueListener)
        greenButton.setOnClickListener(greenListener)
        redButton.setOnClickListener(redListener)
        yellowButton.setOnClickListener(yellowListener)
        startButton.setOnClickListener(startListener)


    }

    private fun fragmentSetup(level: Serializable){
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
        check(Colors.BLUE)
    }

    private val greenListener = View.OnClickListener {
        check(Colors.GREEN)
    }

    private val redListener = View.OnClickListener {
        check(Colors.RED)
    }

    private val yellowListener = View.OnClickListener {
        check(Colors.YELLOW)
    }

    private val startListener = View.OnClickListener {
        startButton.setText("Exit")
        gameFramgment?.start()
    }

    private fun check(color: Colors){
        titleTextView.text = ".."
        val result = gameFramgment?.check(color)
        when (result?.result) {
            Status.CONTINUE -> titleTextView.text = ".."
            Status.COMPLETED -> {
                val handler = Handler()
                handler!!.postDelayed({
                    gameFramgment?.proceed()
                },
                        500
                )


            }
            Status.GAMEOVER -> gameover(result?.value)
        }
    }

    private fun gameover(result: Int){
        val intent = Intent(this, ResultView::class.java)
        intent.putExtra(RESULT_VIEW, result)
        this.startActivity(intent)
    }

    private fun getHexColor(color: Colors): Int{
        val value: Int = when (color) {
            Colors.BLUE -> R.color.colorBlue
            Colors.GREEN -> R.color.colorGreen
            Colors.RED -> R.color.colorRed
            else -> R.color.colorYellow
        }
        return resources.getColor(value)
    }

    override fun show(color: Colors, index: Int) {

        val view: ImageButton = when (color) {
            Colors.BLUE -> blueButton
            Colors.GREEN -> greenButton
            Colors.RED -> redButton
            else -> yellowButton
        }

        val buttonColor = getHexColor(color)


        val animator = ObjectAnimator.ofFloat(1f, 0f)
        animator.addUpdateListener { animation ->
            val initial = animation.animatedValue as Float

            val alphaColor = adjustAlpha(buttonColor, 0.7F)
            view.setColorFilter(alphaColor, PorterDuff.Mode.LIGHTEN)
            if (initial.toDouble() == 0.0) {
                view.colorFilter = null
            }
        }

        animator.duration = 100
//        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = 1
        animator.startDelay = (index  * 400).toLong()
        animator.start()
        Log.d("dkjsl", color.toString())

    }


    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}
