package week3.day19

import util.withInput

fun main () {
    println("day$DAY, part1 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { body ->
        val input = body.split ("\n")
        val towels = input.first().split(", ")
        val designs = input.drop(2)

        fun countOptions(it: String): Long {
            val options = LongArray(it.length + 1).apply { this[it.length] = 1 }

            it.indices.reversed().forEach { i ->
                towels.forEach { towel ->
                    if (it.startsWith(towel, i)) {
                        options[i] += options[i + towel.length]
                    }
                }
            }

            return options.first()
        }

        val part1 = designs.count { countOptions(it) > 0 }
        println (part1)
        val part2 = designs.sumOf { countOptions(it) }
        println (part2)

    }
    return
}

