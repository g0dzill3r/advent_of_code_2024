package week3.day19

import util.withInput

val DAY = 19
val SAMPLE = false

fun main () {
    withInput (DAY, SAMPLE) { input ->
        val rows = input.split("\n")
        val available = rows[0].split(", ").toList()
        val desired = rows.subList(2, rows.size)

        // part1

        val possible = desired.filter { possible (available, it) > 0 }.count()
        println(possible)

        // part2

        val total = desired.map { possible (available, it) }.sum ()
        println (total)
    }

    return
}

// EOF