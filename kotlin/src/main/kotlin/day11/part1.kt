package day11

import util.repeat
import util.withInput


val DAY = 11
val SAMPLE = false

fun main () {
    println("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val model = Model (input)
        println ("START: ${model.stones}")
        for (i in 1 .. 25) {
            println (i)
            model.tick ()
        }
        println (model.stones.size)
    }
    return
}

// EOF
