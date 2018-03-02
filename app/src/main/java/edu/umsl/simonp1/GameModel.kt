package edu.umsl.simonp1

import android.util.Log
import java.util.Random

class GameModel(level: Level? ) {

    var level: Level
    var length: Int = 1
    var duration: Int = 200
    var sequence: ArrayList<Colors>? = null
    var userSequence: ArrayList<Colors>? = null
    var index = 0




    init {
        this.level = level ?: Level.EASY

        sequence = ArrayList()
        userSequence = ArrayList()

        when(level){ // easy is the default
            Level.INTERMEDIATE -> setup(length *3, duration /2)
            Level.DIFFICULT -> setup(length * 6,duration /4 )
        }

        while( length > 1 ){
            sequence?.add(randomColor())
            length--
        }
    }

    private fun setup(length: Int, duration: Int){
        this.length = length
        this.duration = duration
    }

    fun start(){
        index = 0
        proceed()

    }

    fun sequence(): ArrayList<Colors>? {
        return sequence

    }

    data class Tuple(val correct: Boolean = false , val wholeSeqIsCorrect: Boolean = false)

    fun check(color: Colors): Tuple {
        val i = index
        index = (index+1)%sequence?.size!!
        Log.e("Check","              $i and index is $index")

        return Tuple (color == sequence?.get(i), i == sequence?.size!!-1)
    }

    fun proceed (){
        sequence?.add(randomColor())
        Log.e("sequence", "         is $sequence")


    }

    private fun randomColor():Colors{
        var random: Random = Random()
        return Colors.getColor(random.nextInt(4))!!
    }


}
