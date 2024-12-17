package week3.day16.part2

import util.withInput
import week3.day16.Direction
import week3.day16.part1.DAY
import week3.day16.part1.SAMPLE

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val maze = Maze.parse (input)
        maze.dump ()

        val pathMap = maze.walk (maze.start, maze.end, Direction.EAST)
        val cheapest = pathMap.paths
        println ("Found ${cheapest.size} paths of cost ${pathMap.minCost[maze.end]}")
        println (pathMap.minCost.size)
    }
    return
}

// EOF
