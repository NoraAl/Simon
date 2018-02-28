package edu.umsl.simonp1

import java.util.Random

class GameModel(level: Level? ) {

    var level: Level
    var length: Int = 1
    var duration: Int = 200
    var sequence: ArrayList<Colors>? = null
    var userSequence: ArrayList<Colors>? = null



    init {
        this.level = level ?: Level.EASY

        sequence = ArrayList()
        userSequence = ArrayList()

        when(level){ // easy is the default
            Level.INTERMEDIATE -> setup(length *3, duration /2)
            else -> setup(length * 6,duration /4 )
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
        proceed()
    }

    fun currentColor(): Colors? {
        return sequence?.get(0)
    }

    fun addToSequence(color: Colors){
        userSequence?.add(color)
        // todo: check if the user color match the currnet pointed color
        // todo: if not then error, but highlight the correct current pointed color
        // todo: if last pointed color ,i.e. correct sequence, make buttons uncklickables
        // todo: then call function proceed
        println("\t\tUser sequence: $userSequence")
    }

    private fun proceed (){
        sequence?.add(randomColor())
        showSequence()


    }

    private fun showSequence(){
        //todo: implement
        println("\t\tSequence:$sequence")
    }

    private fun randomColor():Colors{
        var random: Random = Random()
        return Colors.getColor(random.nextInt(4))!!
    }


}
