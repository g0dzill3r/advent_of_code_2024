package week3.day15.part1

import util.withInput

val DAY = 15
val SAMPLE = false

fun main () {
    println("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val (grid, moves) = parse (input)

        // Videogame mode

        // Play out the part1 moves

        moves.forEach {
            grid.move (it)
        }
        var total = 0
        grid.visit { point, thing ->
            if (thing == Thing.BOX) {
                total += grid.toGps (point)
            }
        }
        println (total)
    }
    return
}

// EOF