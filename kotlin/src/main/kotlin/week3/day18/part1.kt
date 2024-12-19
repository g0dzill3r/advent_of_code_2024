package week3.day18

import util.repeat
import util.withInput

val DAY = 18
val SAMPLE = true
val dim = Point (7, 7)

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val memory = Memory.parse (dim.row, dim.col, input)
        memory.dump ()
        while (memory.tick ()) {
            // EMPTY
        }
        memory.dump ()
    }
    return
}

// EOF