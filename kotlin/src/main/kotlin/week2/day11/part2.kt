package week2.day11

import util.repeat
import util.withInput

fun main () {
    println("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val model = ModelTwo (input)
        75.repeat {
            model.tick ()
        }
        println (model.stoneCount)
    }
    return
}

// EOF