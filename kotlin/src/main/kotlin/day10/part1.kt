package day10

import util.withInput

val DAY = 10
val SAMPLE = false

fun main () {
    println("day$DAY, part1")
    withInput(DAY, SAMPLE) { input ->
        val grid = Grid(input)
        val trailheads = grid.trailheads
        println ("Found ${trailheads.size} trailheads")

        val hikes = trailheads.map {
            grid.getHikes (it)
        }

        var total = 0
        hikes.forEachIndexed { i, hike ->
            val terminals = hike.map { it[it.size - 1] }.toSet ()
            total += terminals.size
        }
        println (total)
    }
    return
}


// EOF