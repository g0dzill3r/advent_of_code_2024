package day11

import util.repeat
import util.withInput


val DAY = 11
val SAMPLE = false

fun main () {
    println("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val model = ModelOne (input)
        for (i in 1 .. 25) {
            model.tick ()
        }
        println (model.stones.size)
    }
    return
}

// EOF
