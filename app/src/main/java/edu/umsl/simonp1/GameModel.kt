package edu.umsl.simonp1

import android.util.Log

class GameModel(level: Level?) {

    var level: Level
    var levelName: String = level.toString()
    var initialLength: Int = 1
    var speed: Int = 200


    init {
        this.level = level ?: Level.EASY
        when(level){
            Level.EASY -> Log.e("Error", "easy")
            Level.INTERMEDIATE -> setup(initialLength*3, speed/3)
            else -> setup(6,50 )
        }

    }

    private fun setup(initialLength: Int, speed: Int){
        this.initialLength = initialLength
        this.speed = speed
        Log.e("Error", levelName)
    }

}
