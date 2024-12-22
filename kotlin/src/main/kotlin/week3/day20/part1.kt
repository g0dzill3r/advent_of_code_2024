package week3.day20

import util.withInput

val DAY = 20
val SAMPLE = false

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val maze = Maze.parse (input)
        val graph = maze.graph
        val baseTime = graph.race ()

        val cheats = maze.possibleCheats.filter { cheat ->
            baseTime - graph.race (cheat) >= 100
        }
        println (cheats.size)
    }
    return
}

// EOF