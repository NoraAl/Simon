package edu.umsl.simonp1

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.Activity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import android.graphics.PorterDuff
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.Button
import android.widget.ImageButton
import java.io.Serializable
import android.widget.Toast




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

    override fun onPause() {
        super.onPause()
        Log.e("onPause", " --------------")
    }

    override fun onStop() {
        super.onStop()
        Log.e("GameActivity", "----> onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("GameActivity", "----> onDestroy")
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
        animate(blueButton)
        this.check(Colors.BLUE)
    }

    private val greenListener = View.OnClickListener {
        animate(greenButton)
        check(Colors.GREEN)
    }

    private val redListener = View.OnClickListener {
        animate(redButton)
        check(Colors.RED)
    }

    private val yellowListener = View.OnClickListener {
        animate(yellowButton)
        check(Colors.YELLOW)
    }

    private val startListener = View.OnClickListener {
        startButton.setText("Exit")
        println()
        gameFramgment?.start()
    }

    private fun check(color: Colors){

        val result = gameFramgment?.check(color)

        when (result?.result) {
            //Status.CONTINUE -> titleTextView.text = ".."
            Status.COMPLETED -> {
                val handler = Handler()
                val context = applicationContext
                val text = "correct!"
                val duration = result.duration.toInt()/4


                val toast = Toast.makeText(context, text, duration)
                toast.show()
                handler!!.postDelayed({
                    gameFramgment?.proceed()
                },
                        result.duration*5
                )


            }
            Status.GAMEOVER -> gameover(result?.value)
        }
    }

    private fun gameover(result: Int){
        val resultIntent = Intent(this, ResultView::class.java)
        resultIntent.putExtra(RESULT_VIEW, result)
        startActivity(resultIntent)
        finish()
    }


    private fun getHexColor(color: Colors): Int{
        val value: Int = when (color) {
            Colors.BLUE -> R.color.colorLBlue
            Colors.GREEN -> R.color.colorLGreen
            Colors.RED -> R.color.colorRed
            else -> R.color.colorYellow
        }
        return resources.getColor(value)
    }


    override fun show(color: Colors, duration: Long, index: Int) {
        val view: Button = when (color) {
            Colors.BLUE -> blueButton
            Colors.GREEN -> greenButton
            Colors.RED -> redButton
            else -> yellowButton
        }

        animate(view, duration, index)
    }

    private fun animate(view: Button, duration: Long = 200, index: Int = 0){
        val anim = ObjectAnimator.ofFloat(view, "Alpha", 0f, 1f)
        anim.repeatMode = 1
        anim.duration = duration
        anim.repeatMode = Animation.REVERSE

        val elevationAnim = ObjectAnimator.ofFloat(view,"Elevation", 10f)
        elevationAnim.repeatCount = 1
        elevationAnim.repeatMode = Animation.REVERSE

        val animSet = AnimatorSet()
        animSet.duration = duration* 2
        animSet.startDelay =  (index  * (duration*3))
        animSet.play(elevationAnim).with(anim);
        animSet.start();
    }

    override fun freeze() {
        blueButton.isClickable = false
        greenButton.isClickable = false
        redButton.isClickable = false
        yellowButton.isClickable = false
    }

    override fun unfreeze() {
        blueButton.isClickable = true
        greenButton.isClickable = true
        redButton.isClickable = true
        yellowButton.isClickable = true
    }

}

