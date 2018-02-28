package edu.umsl.simonp1

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.util.Log

class GameFramgment: Fragment() {

    private var level: Level? = null
    private var gameModel: GameModel? = null

    var listener: MainFragmentListener? = null
    private var handler: Handler? = null


    interface MainFragmentListener {
        fun show(color: Colors)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        retainInstance = true

        if (savedInstanceState != null) {
            this.level = savedInstanceState.getSerializable(LEVEL) as Level
            Log.e("OnCreate ", level.toString())
        } else {
            val bundle = arguments
            level = bundle.getSerializable(LEVEL) as Level
            Log.e("OnCreate ", level.toString())
        }
        Log.e("FRAGMENT", "FRAGMENT was Created!!!")

        gameModel = GameModel(level)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("FRAGMENT", "FRAGMENT was Destroyed!!!")
    }

    fun start(){
        gameModel?.start()
    }

    fun addToSequence (color: Colors){
        gameModel?.addToSequence(color)
    }

    private var runnable = object : Runnable {
        override fun run() {
            Log.e("FRAGMENT", "Running in the runnable with the runnings.")
            listener?.show(gameModel?.currentColor()!!)
//            handler?.postDelayed(this, 1000)
        }
    }

    fun startSequence() {
        if (handler == null) {
            handler = Handler()
            handler!!.postDelayed(runnable, 1500)
        } else {
            Log.e("FRAGMENT", "Handler already created!")
        }
    }

}
