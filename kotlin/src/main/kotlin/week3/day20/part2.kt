package week3.day20

import util.increment
import util.withInput

val SAMPLE = false
//val MIN_SAVINGS = 50
//val DURATION = 20
val MIN_SAVINGS = 100
val DURATION = 20

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val maze = Maze.parse (input)
        val graph = maze.graph

        val baseTime = graph.race ()
        val cheats = graph.possibleCheats (DURATION, MIN_SAVINGS)
        println ("Considering ${cheats.size} possible cheats.")

        var total = 0L
        val tally = mutableMapOf<Int, Int> ()

        cheats.forEach { cheat ->
            val time = graph.race (cheat)
            val savings = baseTime - time
            if (savings >= MIN_SAVINGS) {
                tally.increment (savings)
                total ++
            }
        }
        println (total)
    }
    return
}


// 897262 - too low

// EOF
