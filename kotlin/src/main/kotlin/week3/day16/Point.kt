package week3.day16

import java.util.regex.Pattern

data class Point (val row: Int, val col: Int) {
    fun add (other: Point) = Point(row + other.row, col + other.col)
    fun add (dir: Direction) = Point(row + dir.drow, col + dir.dcol)
    override fun toString (): String = "($row, $col)"

    fun direction (other: Point): Direction {
        for (dir in Direction.entries) {
            if (add (dir) == other) {
                return dir
            }
        }
        throw IllegalStateException ()
    }

    companion object {
        private val pattern = Pattern.compile ("\\((\\d+),(\\d+)\\)")
        fun parse (str: String): Point {
            val matcher = pattern.matcher (str)
            if (matcher.matches ()) {
                return Point (matcher.group (1).toInt (), matcher.group (2).toInt ())
            } else {
                throw IllegalArgumentException ("Malfoed Point: $str")
            }
        }
    }
}

// EOF