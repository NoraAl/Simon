package edu.umsl.simonp1

import android.util.Log
import java.util.Random

class GameModel(level: Level? ) {

    var level: Level
    var length: Int = 1
    var duration: Long = 200
    var sequence: ArrayList<Colors>? = null
    var userSequence: ArrayList<Colors>? = null
    var index: Int = 0
    var userIndex = 0




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

    data class Triple (val result: Status, val value: Int, val duration: Long)

    fun check(color: Colors): Triple {

        val i = index
        index = (index+1)%sequence?.size!!

        // game over
        if (color != sequence?.get(i))
            return Triple (Status.GAMEOVER, userIndex, duration)

        userIndex++
        // whole sequence is correct, now show the new sequence
        if (i == sequence?.size!!-1) {
            userIndex = 0
            return Triple(Status.COMPLETED, 0, duration)
        }

        //current color is correct, keep getting the input from the user
        return Triple(Status.CONTINUE,0, duration )
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
