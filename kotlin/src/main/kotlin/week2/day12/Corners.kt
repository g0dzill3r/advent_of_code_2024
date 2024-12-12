package week2.day12

import util.withInput



fun main () {
    println("day$DAY, part2")
    withInput(DAY, true) { input ->
        val gardens = Gardens(input)
        gardens.dump ()

//        val regions = gardens.getRegions()
//        regions.forEach { region ->
        val region = gardens.getRegion (Point (0, 0))
            println ("---")
            region.dump ()
            println (region.countCorners ())
//        }
    }
    return
}

// EOF