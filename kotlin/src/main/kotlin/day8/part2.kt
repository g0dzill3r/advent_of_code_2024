package day8

import util.withInput

fun main () {
    withInput(DAY, SAMPLE) { input ->
        val grid = Grid.parse(input)
        val antinodes = grid.getAntinodes2()
        println (antinodes.size)
    }
}

// EOF

