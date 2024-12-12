package week2.day12

import util.withInput

fun main () {
    println("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val gardens = Gardens (input)
        var total = 0
        val regions = gardens.getRegions ()
        regions.forEach {
            total += it.bulkPrice
        }
        println (total)
    }
    return
}

// EOF

