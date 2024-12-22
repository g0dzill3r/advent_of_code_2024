package week3.day16.part2

import util.withInput
import week3.day16.Direction
import week3.day16.Point

val DAY = 16
val SAMPLE = false

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val maze = Maze.parse (input)
        val graph = maze.buildGraph ()
        maze.walk (graph)

        val shortest = maze.shortest (graph)
        val points = mutableSetOf<Point> ()
        shortest.forEachIndexed { i, path ->
            path.forEach {
                points.add (it)
            }
        }
        println (points.size)
    }
    return
}

// EOF
