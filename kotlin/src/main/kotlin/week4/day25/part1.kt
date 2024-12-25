package week4.day25

import util.withInput

val DAY = 25
val SAMPLE = false

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)

        var total = 0
        model.locks.forEach { lock ->
            model.keys.forEach { key ->
                if (! key.overlaps(lock)) {
                    total ++
                }
            }
        }
        println (total)
    }
    return
}

// EOF