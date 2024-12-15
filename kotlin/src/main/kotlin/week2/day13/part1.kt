package week2.day13

import util.withInput

val DAY = 13
val SAMPLE = true

fun main () {
    println("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val arcade= Arcade (input)
        var total = 0L
        arcade.machines.forEach {
            val possibilities = it.possibilities ()
            if (possibilities.isNotEmpty()) {
                val cheapest = possibilities.sortedBy { it.cost }.first()
                total += cheapest.cost
            }
        }
        println (total)
    }
    return
}

// EOF