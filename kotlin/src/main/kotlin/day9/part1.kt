package day9

import util.withInput


val DAY = 9
val SAMPLE = false

fun main () {
    println ("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val model = Model (input)
        model.compact ()
        println ("checksum=${model.checksum ()}")
    }
    return
}

// EOF