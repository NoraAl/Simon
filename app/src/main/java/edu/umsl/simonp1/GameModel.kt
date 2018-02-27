package edu.umsl.simonp1

import android.util.Log
import java.util.Random;

class GameModel(level: Level?) {

    var level: Level
    var levelName: String = level.toString()
    var initialLength: Int = 1
    var duration: Int = 200
    var sequence: ArrayList<Colors>? = null


    init {
        this.level = level ?: Level.EASY

        when(level){
            Level.EASY -> Log.e("Error", "easy")
            Level.INTERMEDIATE -> setup(initialLength*3, duration /3)
            else -> setup(6,50 )
        }


        // todo: a. generate random number and add it to the seq, show it for the specified length
        // todo: b. get the user seq, match it, and then fail or go back to a

        sequence = ArrayList()

        while( initialLength > 0 ){
            val y :Colors? = getRandom()
            sequence?.add(y!!)
            initialLength--
        }

    }

    private fun setup(initialLength: Int, duration: Int){
        this.initialLength = initialLength
        this.duration = duration
        Log.e("Error", levelName)
    }

    private fun getRandom():Colors?{
        var random: Random = Random()
        return Colors.getColor(random.nextInt(4))
    }


}
