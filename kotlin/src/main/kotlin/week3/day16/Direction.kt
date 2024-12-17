package week3.day16

import week3.day15.Point

enum class Direction (val symbol: Char, val drow: Int, val dcol: Int) {
    NORTH ('^', -1, 0),
    EAST ('>', 0, 1),
    SOUTH ('v', 1, 0),
    WEST ('<', 0, -1);

    val left: Direction
        get () = when (this) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
        }

    val right: Direction
        get () = when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }

    val delta: Point = Point (drow, dcol)
    
    companion object {
        private val fromSymbol = entries.associateBy { it.symbol }
        fun fromSymbol(symbol: Char) = fromSymbol[symbol] as Direction
    }
}

// EOF