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
        fun show(color: Colors, index: Int)


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
        showSequence()
    }

    fun proceed(){
        gameModel?.proceed()
        showSequence()
    }

    fun check(color: Colors): Status{
        val result = gameModel?.check(color)
        if (result?.correct == false){
            //gameover
            return Status.GAMEOVER
        }
        if (result?.correct!! && result?.wholeSeqIsCorrect){
            // message: good job
            // end the game and clean resources
            return Status.COMPLETED
        }
        return Status.CONTINUE
    }

//    private var runnable = object : Runnable(color:Colors) {
//        override fun run() {
//
//
//                listener?.show(sequence?.get(i))
//        }
//    }

    fun showSequence() {
        val sequence = gameModel?.sequence()
        if (handler == null) {
            handler = Handler()
            for (i in 0 until sequence?.size!!)
                handler!!.postDelayed({
                    listener?.show(sequence?.get(i),i)
                }, 300)
        } else {
            for (i in 0 until sequence?.size!!)
                listener?.show(sequence?.get(i), i)
        }
    }

}
