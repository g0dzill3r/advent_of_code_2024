package week3.day18

import util.repeat
import util.withInput

val DAY = 18
//val SAMPLE = true
//val DIM = Point (7, 7)
//val TICKS = 12
val SAMPLE = false
val DIM = Point (71, 71)
val TICKS = 1024

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val memory = Memory.parse (DIM.row, DIM.col, input)
        TICKS.repeat {
            memory.tick ()
        }
        val steps = memory.walk ()
        println (steps)
    }
    return
}

// EOF