package week3.day18

import util.repeat
import util.withInput

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val memory = Memory.parse (DIM.row, DIM.col, input)
        while (true) {
            val tick = memory.tick ()
            if (! memory.reachable (memory.end)) {
                println (tick)
                break
            }
        }
    }
    return
}


// EOF