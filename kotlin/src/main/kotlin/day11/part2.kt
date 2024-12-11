package day11

import util.withInput

fun main () {
    println("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val model = ModelTwo (input)
        for (i in 1 .. 75) {
            model.tick ()
        }
        println (model.stoneCount)
    }
    return
}

// EOF