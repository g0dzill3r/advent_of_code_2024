package week3.day16

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
}

// EOF