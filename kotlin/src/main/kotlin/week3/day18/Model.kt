package week3.day18

import util.repeat

data class Point (val row: Int, val col: Int) {
    override fun toString (): String = "($row, $col)"

    companion object {
        fun parse (input: String): Point {
            val (col, row) = input.split(",")
            return Point (row.toInt(), col.toInt())
        }
    }
}

enum class State (val symbol: Char) {
    WORKING ('.'),
    DAMAGED ('#')
}

data class Memory (val rows: Int, val cols: Int, val seq: List<Point>) {
    var me = Point (0, 0)
    var next = 0
    val memory = buildList {
        (rows * cols).repeat {
            add (State.WORKING)
        }
    }.toMutableList ()

    fun toIndex (row: Int, col: Int): Int = col + row * cols
    fun toIndex (point: Point): Int = toIndex (point.row, point.col)
    fun get (point: Point): State = memory [toIndex (point)]
    fun set (point: Point, state: State) {
        memory [toIndex (point)] = state
        return
    }

    fun visit (func: (Point, State) -> Unit) {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val point = Point (row, col)
                val state = get (point)
                func (point, state)
            }
        }
    }

    fun dump () {
        visit { point, state ->
            if (point == me) {
                print ("*")
            } else {
                print(get(point).symbol)
            }
            if (point.col == cols - 1) {
                println ()
            }
        }
        println ("me=$me")
        return
    }

    fun tick (): Boolean {
        return if (next < seq.size) {
            set (seq[next++], State.DAMAGED)
            true
        } else {
            false
        }
    }

    companion object {
        fun parse ( rows: Int, cols: Int, input: String): Memory {
            val seq = buildList {
                input.split ("\n").forEach {
                    add (Point.parse (it))
                }
            }
            return Memory (rows, cols, seq)
        }
    }
}