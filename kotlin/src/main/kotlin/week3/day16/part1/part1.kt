package week3.day16.part1

import util.withInput
import week3.day16.Direction

val DAY = 16
val SAMPLE = true

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val maze = Maze.parse (input)
        maze.dump ()

        val cheapest = maze.walk (maze.start, maze.end, Direction.EAST)
        println (cheapest)
    }
    return
}

// EOF