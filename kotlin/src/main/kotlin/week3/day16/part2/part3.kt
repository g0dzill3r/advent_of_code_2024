package week3.day16.part2

import util.interactive
import util.withInput
import week3.day16.Direction
import week3.day16.Point
import week3.day16.part2.DAY
import week3.day16.part2.SAMPLE

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val maze = Maze.parse(input)
        maze.dump()

        // Build a graph from the maze

        var graph = maze.buildGraph()
        maze.walk (graph)

        var pos = Maze.Position(maze.start, Direction.EAST)
        fun updated () {
            println (graph[pos])
        }
        updated ()

        interactive ("d16> ") { str ->
            var args = str.split (Regex ("\\s+")).filter { it.isNotEmpty () }
            if (args.size > 0) {
                when (args[0]) {
                    "f" -> {
                        val node = graph[pos] as Maze.Node
                        if (node.hasEdge (pos.forward)) {
                            pos = pos.forward
                            updated()
                        } else {
                            println ("No edge forward.")
                        }
                    }
                    "l" -> {
                        pos = pos.left
                        updated ()
                    }
                    "r" -> {
                        pos = pos.right
                        updated ()
                    }
                    "?" -> updated ()
                    "g" -> {
                        if (args.size != 2) {
                            println ("USAGE: ${args[0]} (#,#)")
                        } else {
                            pos = Maze.Position (Point.parse(args[1]), pos.dir)
                        }
                    }
                    "p" -> {
                        maze.cur = pos
                        maze.dump ()
                    }
                    "s" -> {
                        val shortest = maze.shortest (graph)
                        val points = mutableSetOf<Point> ()
                        shortest.forEachIndexed { i, path ->
                            path.forEach {
                                points.add (it)
                            }
                        }
                        println (points.size)
                    }
                    else -> println ("ERROR: Unknown command: ${args[0]}")
                }
            }
            false
        }

//        val graph = maze.asGraph ()

    }
    return
}

// EOF