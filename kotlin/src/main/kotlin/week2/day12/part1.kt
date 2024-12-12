package week2.day12

import util.withInput

val DAY = 12
val SAMPLE = false

fun main () {
    println("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val gardens = Gardens (input)
        val regions = gardens.getRegions ()
        var total = 0
        regions.forEach {
            total += it.price
        }
        println (total)
    }
    return
}

// EOF