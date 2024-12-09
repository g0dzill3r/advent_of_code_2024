package day8

import util.withInput

val DAY = 8
val SAMPLE = false

fun main () {
    withInput(DAY, SAMPLE) { input ->
        val grid = Grid (input)
        val antinodes = grid.getAntinodes()
        println (antinodes.size)
    }
    return
}

// EOF


