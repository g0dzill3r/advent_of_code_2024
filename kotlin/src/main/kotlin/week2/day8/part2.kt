package week2.day8

import util.withInput

fun main () {
    withInput(DAY, SAMPLE) { input ->
        val grid = Grid2 (input)
        val antinodes = grid.getAntinodes ()
        println (antinodes.size)
    }
}

// EOF

