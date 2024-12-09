package util

data class Point2 (val row: Int, var col: Int) {
    operator fun plus (other: Point2): Point2 = Point2 (row + other.row, col + other.col)
    operator fun minus (other: Point2): Point2 = Point2 (row - other.row, col - other.col)
    override fun toString (): String = "($row, $col)"
}

fun main () {
    val a = Point2 (10, 15)
    val b = Point2 (5, 10)

    println (a)
    println (b)
    println (a+b)
    println (a-b)
}

// EOF