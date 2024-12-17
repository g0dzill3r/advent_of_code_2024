package week3.day17

import util.withInput


val DAY = 17
val SAMPLE = true

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else "}"}")
    withInput(DAY, SAMPLE) { input ->
        val computer = Computer.parse (input)
        computer.dump ()
        val output = computer.run ()
        println (output.joinToString (","))
    }
    return
}

// EOF