package week3.day15

fun List<String>.catenate (): String {
    return buildString {
        this@catenate.forEach {
            append(it)
        }
    }
}

enum class Direction (val symbol: Char, val drow: Int, val dcol: Int) {
    UP ('^', -1, 0),
    RIGHT ('>', 0, 1),
    DOWN ('v', 1, 0),
    LEFT ('<', 0, -1);

    val opposite: Direction
        get () = when (this) {
            UP -> DOWN
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
        }
    val delta: Point = Point (drow, dcol)
    companion object {
        private val fromSymbol = entries.associateBy { it.symbol }
        fun fromSymbol(symbol: Char) = fromSymbol[symbol] as Direction
    }
}

data class Point (val row: Int, val col: Int) {
    fun add (other: Point) = Point(row + other.row, col + other.col)
    fun add (dir: Direction) = Point(row + dir.drow, col + dir.dcol)
    override fun toString (): String = "($row, $col)"
}

// EOF