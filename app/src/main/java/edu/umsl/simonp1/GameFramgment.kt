package edu.umsl.simonp1

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.util.Log
import java.util.*

class GameFramgment: Fragment() {

    private var level: Level? = null
    private var gameModel: GameModel? = null

    var listener: MainFragmentListener? = null
    private var handler: Handler? = null


    interface MainFragmentListener {
        fun show(color: Colors, duration: Long, index: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // retainInstance = true

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
        showSequence()
    }

    fun proceed(){
        gameModel?.proceed()
        showSequence()
    }

    fun check(color: Colors): GameModel.Triple {
        return gameModel?.check(color)!!
    }

//    val sequenceRunnable = object: Runnable {
//        val sequence = gameModel?.sequence()
//        val duration = gameModel?.duration!!
//        override fun run()  {
//            for (i in 0 until sequence?.size!!)
//                listener?.show(sequence?.get(i), duration, i)
//
//        }
//    }


    fun showSequence() {
        val sequence = gameModel?.sequence()
        val duration = gameModel?.duration!!

        if (handler == null) {
            handler = Handler()
            handler!!.postDelayed({
                for (i in 0 until sequence?.size!!)
                    listener?.show(sequence?.get(i), duration, i)

            }, 500)
            Log.e("Handler", "------- after")
        } else {
            handler!!.postDelayed({
                for (i in 0 until sequence?.size!!)
                    listener?.show(sequence?.get(i), duration, i)

            }, 500)
            Log.e("Handler", "------- after")
        }
    }

}
