package week2.day13

import util.withInput

val prizeOffset = 10_000_000_000_000L

fun main () {
    println("day${DAY}, part2")
    withInput(DAY, SAMPLE) { input ->
        val arcade = Arcade (input, prizeOffset)
        var total = 0L

//        arcade.machines.forEach {
//            val possibilities = it.possibilities ()
//            if (possibilities.isNotEmpty()) {
//                val cheapest = possibilities.sortedBy { it.cost }.first()
//                total += cheapest.cost
//            }
//        }

        val machine = arcade.machines[0]
        println (machine)
    }
    return
}

// EOF

