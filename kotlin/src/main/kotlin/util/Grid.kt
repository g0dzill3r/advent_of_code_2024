package util

class Grid<T> (val input: String, val func: (Char) -> T) {
    val split = input.split ("\n")
    val rows = split.size
    val cols = split[0].length
    val data = input.replace ("\n", "").map { func (it) }

    fun toIndex (point: Point2) = point.row * cols + point.col
    fun thingAt (point: Point2): T = data[toIndex (point)]

    fun find (match: T): List<Point2> {
        return buildList {
            visit { p, t ->
                if (match == t) {
                    add (p)
                }
            }
        }
    }

    fun visit (func: (point: Point2, el: T) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val p = Point2 (row, col)
                func (p, thingAt (p))
            }
        }
    }

    fun dump (func: (T) -> String) {
        visit { (row, col), t ->
            print (func (t))
            if (col == cols - 1) {
                println ()
            }
        }
    }
}

enum class TicTacToe (val c: Char){
    X ('x'), Y ('y'), NONE ('.');
    companion object {
        private val map = TicTacToe.entries.associateBy { it.c }
        fun parse (c: Char) = map[c] !!
    }
}

fun main () {
    val grid = Grid ("x.y\n.xy\ny.xy") {
        TicTacToe.parse (it)
    }
    println (grid)
    grid.dump {
        "" + it.c
    }
    println (grid.find (TicTacToe.X))
    return
}

// EOF
