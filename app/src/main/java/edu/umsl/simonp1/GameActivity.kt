package edu.umsl.simonp1

import android.animation.AnimatorInflater
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import android.animation.ValueAnimator
import android.graphics.PorterDuff
import android.animation.ObjectAnimator
import android.graphics.Color
import android.widget.ImageButton
import android.widget.ImageView


private const val GAME_FRAGMENT_TAG = "GameFramgment"
const val LEVEL = "Level"

@Suppress("DEPRECATION")
class GameActivity : Activity(), GameFramgment.MainFragmentListener {


    var gameFramgment: GameFramgment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var level = intent.getSerializableExtra(MainActivity.LEVEL_KEY)

        titleTextView.text = "PLAYING "+ level.toString() + " LEVEL"
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
        this.adjustAlpha(R.color.colorRed,0.5F)
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

    private fun getHexColor(color: Colors): Int{
        val value: Int = when (color) {
            Colors.BLUE -> R.color.colorBlue
            Colors.GREEN -> R.color.colorGreen
            Colors.RED -> R.color.colorRed
            else -> R.color.colorYellow
        }
        return resources.getColor(value)
    }

    override fun show(color: Colors) {

        val view: ImageButton = when (color) {
            Colors.BLUE -> blueButton
            Colors.GREEN -> greenButton
            Colors.RED -> redButton
            else -> yellowButton
        }

        val buttonColor = getHexColor(color)


        val colorAnim = ObjectAnimator.ofFloat(1f, 0f)
        colorAnim.addUpdateListener { animation ->
            val initial = animation.animatedValue as Float

            val alphaColor = adjustAlpha(buttonColor , 0.7F)
            view.setColorFilter(alphaColor, PorterDuff.Mode.LIGHTEN)
            if (initial.toDouble() == 0.0) {
                view.colorFilter = null
            }
        }

        colorAnim.duration = 150
//        colorAnim.repeatMode = ValueAnimator.RESTART
        colorAnim.repeatCount = 1
        colorAnim.start()

    }


    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}
