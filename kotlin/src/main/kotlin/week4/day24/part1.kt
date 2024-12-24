package week4.day24

import util.withInput

val DAY = 24
val SAMPLE = false

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse(input)
        model.part1 ()
        println ("value=${model.output}")
    }
    return
}

// EOF}