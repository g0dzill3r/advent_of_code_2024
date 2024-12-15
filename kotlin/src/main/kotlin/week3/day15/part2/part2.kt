package week3.day15.part2

import util.withInput
import week3.day15.Direction
import week3.day15.part1.DAY
import week3.day15.part1.SAMPLE

fun main () {
    println("day$DAY, part2")
    withInput(DAY, false) { input ->
        val (grid, moves) = parse(input)

        // Videogame mode

        grid.dump ()
        println ("${moves.size} moves")

        while (true) {
            val input = readln()
            if (input.isNotEmpty()) {
                val dir = when (input[0]) {
                    '^', 'w' -> Direction.UP
                    '<', 'a' -> Direction.LEFT
                    '>', 'd' -> Direction.RIGHT
                    'v', 'x' -> Direction.DOWN
                    else -> continue
                }
                grid.move(dir)
                grid.dump()
            }
        }

        // Play out the part1 moves

        moves.forEach {
            grid.move (it)
        }
        var total = 0L
        grid.visit { point, thing ->
            if (thing == Thing.BOX_LEFT) {
                total += grid.toGps (point)
            }
        }
        println (total)
    }
    return
}

// EOF