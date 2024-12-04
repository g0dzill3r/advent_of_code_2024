package day4

import util.InputUtil

val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)

    val grid = Grid.parse (input)
//    grid.dump (true)
//    grid.dumpXmas()

    var total = 0
    for (col in 0 until grid.cols) {
        for (row in 0 until grid.rows) {
            if (grid.isXmas (row, col)) {
                total ++
            }
        }
    }
    println (total)
    return
}