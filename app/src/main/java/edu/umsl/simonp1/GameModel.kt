package edu.umsl.simonp1

/**
 * Created by nor on 2/23/18.
 */
class GameModel(_level: Int? = 0) {

    // should be enum
    var level: Int

    init {
        level = _level ?: 0

    }


    fun isLevelEasy(value: Int): Boolean {
        println("is level easy?  ")
        return value == level
    }
}
