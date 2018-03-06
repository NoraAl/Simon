package edu.umsl.simonp1

import android.app.Fragment
import android.os.Bundle

class GameFramgment: Fragment() {
    private var level: Level? = null
    private var gameModel: GameModel? = null
    var listener: MainFragmentListener? = null


    interface MainFragmentListener {
        fun show(colors: ArrayList<Colors>, duration: Long)
        fun freeze()
        fun unfreeze()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            this.level = savedInstanceState.getSerializable(LEVEL) as Level
        } else {
            val bundle = arguments
            level = bundle.getSerializable(LEVEL) as Level
        }
        gameModel = GameModel(level)
    }

    fun proceed(start: Boolean = false){
        listener?.freeze()
        if (start)
            gameModel?.start()
        else
            gameModel?.proceed()
        showSequence()
        listener?.unfreeze()
    }

    fun check(color: Colors): GameModel.Triple {
        return gameModel?.check(color)!!
    }


    fun showSequence() {
        val sequence = gameModel?.sequence()
        val duration = gameModel?.duration!!
        listener?.show(sequence!!, duration)
    }

    fun getSequence():ArrayList<Colors>?{
        return gameModel?.sequence
    }

}
