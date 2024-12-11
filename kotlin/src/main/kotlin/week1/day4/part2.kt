package week1.day4

import util.InputUtil

val SAMPLE = false;

fun main () {
    val input = InputUtil.getInput(DAY, SAMPLE)
    val grid = Grid.parse (input)

    var total = 0
    grid.visit { row, col, _ ->
        if (grid.isXmas (row, col)) {
            total ++
        }
    }
    println (total)
    return
}