package day4

import util.InputUtil

val DAY = 4;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val grid = Grid.parse (input.trim ())
    grid.dump ()

    var total = 0
    grid.visit { row, col, value ->
        for (dir in Direction.entries) {
            if (grid.check (row, col, dir, "XMAS")) {
                total ++
            }
        }
    }
    println (total)
    return
}

enum class Direction (val drow: Int, val dcol: Int) {
    N (0, -1),
    NE (1, -1),
    E (1, 0),
    SE (1, 1),
    S (0, 1),
    SW (-1, 1),
    W (-1, 0),
    NW (-1, -1)
}

data class Grid (
    var chars: List<Char>,
    val rows: Int,
    val cols: Int
) {
    fun check (row: Int, col: Int, dir: Direction, str: String): Boolean {
        if (! isValid (row, col)) {
            return false
        }
        return if (charAt (row, col) == str [0]) {
            if (str.length == 1) {
                true
            } else {
                check (row + dir.dcol, col + dir.drow, dir, str.substring (1, str.length))
            }
        } else {
            false
        }
    }

    fun visit (func: (Int, Int, Char) -> Unit) {
        for (col in 0 until cols) {
            for (row in 0 until rows) {
                func (row, col, charAt (row, col))
            }
        }
        return
    }

    fun charAt (row: Int, col: Int): Char {
        return chars[col + row * cols]
    }

    fun isValid (row: Int, col: Int): Boolean {
        return row in 0..< rows && col in 0 ..< cols;
    }

    companion object {
        fun parse (input: String): Grid {
            val rows = input.split ("\n")
            val cols = rows[0].length
            val chars = input.replace ("\n", "").toList ()
            return Grid (chars, rows.size, cols)
        }
    }

    fun dump (remove: Boolean = false) {
        println ("rows=$rows cols=$cols")
        val str = buildString {
            for (row in 0 until rows) {
                print ("${row % 10} ")
                for (col in 0 until cols) {
                    val c = charAt (row, col)
                    if (remove && c == 'X') {
                        print (".")
                    } else {
                        print (c)
                    }
                    if (col == cols - 1) {
                        println ()
                    }
                }
            }
        }
        println (str)
    }

    fun isXmas (row: Int, col: Int): Boolean {
        if (row == 0 || col == 0 ||  row == rows - 1 || col == cols - 1) {
            return false
        }
        if (charAt (row, col) != 'A') {
            return false
        }
        val str = "" +
                charAt (row - 1, col - 1) +
                charAt (row -1, col + 1) +
                charAt (row + 1, col - 1) +
                charAt (row + 1, col + 1)
        if (str in valid) {
            return true
        }
        return false
    }

    private val valid = listOf (
        "MMSS", "SMSM", "MSMS", "SSMM"
    )

    private fun isXmasNeighbor (row: Int, col: Int): Boolean {
        fun isValid (row: Int, col: Int): Boolean {
            return row in 1 until rows -1
                    && col in 1 until cols - 1
        }
        val points = listOf (
            -1 to -1,
            -1 to 1,
            1 to -1,
            1 to 1
        )
        for ((rowDelta, colDelta) in points) {
            val r = row + rowDelta
            val c = col + colDelta
            if (isValid (r, c) && isXmas (r, c)) {
                 return true
            }
        }
        return false
    }

    fun dumpXmas (){
        println (buildString {
            for (row in 0 until rows) {
                print ("${row % 10} ")
                for (col in 0 until cols) {
                    if (isXmas(row, col)) {
                        print ("*")
                    } else if (isXmasNeighbor (row, col)) {
                        print (charAt (row, col))
                    } else {
                        print (".")
                    }
                    if (col == cols - 1) {
                        println ()
                    }
                }
            }
        });
    }
}

// EOF