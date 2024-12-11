package day10

import util.withInput


fun main () {
    println("day$DAY, part2")
    withInput(DAY, SAMPLE) { input ->
        val grid = Grid(input)
        val trailheads = grid.trailheads
        println ("Found ${trailheads.size} trailheads")

        val hikes = trailheads.map {
            grid.getHikes (it)
        }

        var total = 0
        hikes.forEachIndexed { i, hike ->
            total += hike.size
        }
        println (total)
    }
    return
}

// EOF