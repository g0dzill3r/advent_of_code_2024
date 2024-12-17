package week3.day16.part2

import util.withInput
import week3.day16.part1.DAY
import week3.day16.part1.SAMPLE

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val maze = Maze.parse(input)
        maze.dump()

        // Build a graph from the maze

//        val graph = maze.asGraph ()

    }
    return
}

// EOF