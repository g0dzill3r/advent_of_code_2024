package week3.day15.part2

import util.withInput
import week3.day15.part1.DAY
import week3.day15.part1.SAMPLE

fun main () {
    println("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val (grid, moves) = parse(input)
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