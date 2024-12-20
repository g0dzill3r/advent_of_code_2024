package week3.day16.part2

import util.withInput
import week3.day16.Direction
import week3.day16.Point

val DAY = 16
val SAMPLE = true

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val maze = Maze.parse (input)
        maze.dump ()

        val graph = maze.buildGraph ()
        val point = Point (1, 13)
        Direction.entries.forEach {
            val pos = graph [Maze.Position(point, it)]
            println ("$it: $pos")
        }
    }
    return
}

// EOF
