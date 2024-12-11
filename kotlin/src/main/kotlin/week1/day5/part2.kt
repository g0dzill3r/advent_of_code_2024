package week1.day5

import util.withInput

fun main () {
    withInput (week1.day5.DAY, week1.day5.SAMPLE) { input ->
        val rules = week1.day5.Rules.Companion.parse(input)
        val invalid = mutableListOf<List<Int>>()

        // Get the invalid page orders

        rules.updates.forEach {
            if (!rules.isOrdered(it)) {
                invalid.add(it)
            }
        }

        // Reorder them properly and then sum the central page numbers

        val reordered = invalid.map { rules.reorder(it) }
        var total = 0
        reordered.forEach {
            total += it[it.size / 2]
        }
        println(total)
    }
    return
}

// EOF