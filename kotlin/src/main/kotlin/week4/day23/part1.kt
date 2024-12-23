package week4.day23

import util.withInput

val DAY = 23

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse (input)
        val rings = model.rings
        val possible = rings.filter {
            it.any {
                it.startsWith("t")
            }
        }
        println (possible.size)
    }
    return
}

// EOF