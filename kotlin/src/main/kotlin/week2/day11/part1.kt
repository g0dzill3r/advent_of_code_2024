package week2.day11

import util.repeat
import util.withInput

val DAY = 11
val SAMPLE = false

fun main () {
    println("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val model = ModelOne (input)
        25.repeat {
            model.tick ()
        }
        println (model.stones.size)
    }
    return
}

// EOF
