package week2.day13

import util.withInput
import kotlin.math.floor

val prizeOffset = 10_000_000_000_000L
//val prizeOffset = 0L

fun main () {
    println("day${DAY}, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val arcade = Arcade (input, prizeOffset)
        var total = 0L

        arcade.machines.forEach {
            val solution = solve (it)
            if (solution != null) {
                total += it.cost (solution)
            }
        }
        println (total)
    }
    return
}

fun solve (machine: Machine): Coordinates?{
    val a1 = machine.a.x
    val a2 = machine.a.y
    val b1 = machine.b.x
    val b2 = machine.b.y
    val c1 = machine.prize.x
    val c2 = machine.prize.y

    val den = (a1*b2 - a2*b1).toDouble()
    val xnum = c1*b2 - b1*c2
    val ynum = a1*c2 - c1*a2

    val x = xnum / den
    val y = ynum / den

    return if (x == floor (x) && y == floor (y)) {
        Coordinates (x.toLong (), y.toLong ())
    } else {
        null
    }
}

// EOF

