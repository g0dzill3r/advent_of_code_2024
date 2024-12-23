package week4.day23

import util.withInput

val SAMPLE = true

fun main () {
    println("day$DAY, part2 ${if (SAMPLE) "(SAMPLE)" else ""}")
    withInput(DAY, SAMPLE) { input ->
        val model = Model.parse (input)

        val sets = mutableSetOf<Set<String>> ().apply {
            model.nodes.forEach {
                add (setOf (it))
            }
        }
        val already = mutableSetOf<String> ()
        model.nodes.forEach { node ->
            val additional = mutableSetOf<Set<String>> ()
            model.getConnections (node).forEach { other ->
                if (other !in already) {
                    sets.forEach { set ->
                        if (other !in set) {
                            if (set.all {
                                    model.areConnected(other, it)
                                }) {
                                additional.add(buildSet {
                                    addAll(set)
                                    add(other)
                                })
                            }
                        }
                    }
                }
            }
            sets.addAll (additional)
            already.add (node)
        }
        val max = sets.maxOf {
            it.size
        }
        val biggest = sets.filter { it.size == max } [0]
        println (biggest.sorted ().joinToString(","))
    }
    return
}

// EOF