package edu.umsl.simonp1

import android.util.Log
import java.util.Random

class GameModel(level: Level? ) {

    var level: Level
    var length: Int = 1
    var duration: Long = 600
    var sequence: ArrayList<Colors>? = null
    var userSequence: ArrayList<Colors>? = null
    var index: Int = 0
    var currentScore = 0

    init {
        this.level = level ?: Level.EASY

        sequence = ArrayList()
        userSequence = ArrayList()

        when(level){ // easy is the default
            Level.INTERMEDIATE -> setup(3, duration /2)
            Level.DIFFICULT -> setup(5,duration /4 )
        }

        while( length > 1 ){
            sequence?.add(randomColor())
            length--
        }
    }

    private fun setup(length: Int, duration: Long){
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

    data class Triple (val result: Status, val currentScore: Int, val duration: Long)

    fun check(color: Colors): Triple {

        val i = index


        // game over
        if (color != sequence?.get(i))
            return Triple (Status.GAMEOVER, currentScore, duration)

        if(currentScore <= index) currentScore++
        index = (index+1)%sequence?.size!!

        // whole sequence is correct, now show the new sequence
        if (i == sequence?.size!!-1) {
            return Triple(Status.COMPLETED, currentScore, duration)
        }

        //current color is correct, keep getting the input from the user
        return Triple(Status.CONTINUE,currentScore, duration )
    }

    fun proceed (){
        sequence?.add(randomColor())
    }

    private fun randomColor():Colors{
        var random = Random()
        return Colors.getColor(random.nextInt(4))!!
    }
}
