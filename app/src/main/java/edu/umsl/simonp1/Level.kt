package edu.umsl.simonp1

enum class Level {EASY, INTERMEDIATE, DIFFICULT}
enum class Status{CONTINUE, COMPLETED, GAMEOVER}


enum class Colors(val color: Int){
    BLUE(0),
    GREEN(1),
    RED(2),
    YELLOW(3);

    companion object {
        private val map = Colors.values().associateBy(Colors::color)
        fun getColor(value: Int) = map[value]
    }
}

